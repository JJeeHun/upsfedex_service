package com.flexfit.comm.svc.ups_core.service.shipment;

import com.flexfit.comm.svc.ups_core.dto.*;
import com.flexfit.comm.svc.ups_core.service.auth.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ShipmentService {
    private final String BASE_URL;
    private final RestTemplate restTemplate;
    private final ObjectMapper om = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final AuthService authService;
//    private final AuthService prodAuthService = new AuthService("joshua.son@flexfit.com", "Flexfit2@2022", "04XY01", true);

    public ShipmentService(String clientId, String clientSecret, String account) {
        this(clientId, clientSecret, account, false);
    }

    public ShipmentService(String clientId, String clientSecret, String account, boolean isProd) {
        this.authService = new AuthService(clientId, clientSecret, account, isProd);
        this.restTemplate = new RestTemplate();
        this.restTemplate.getInterceptors()
            .add((request, body, execution) -> {
                log.info("UPS ShipmentService [ 요청 URI = '{}' ]", request.getURI());
                log.info("UPS ShipmentService [ 요청 DATA = {} ]", new String(body));
                return execution.execute(request, body);
            });
        this.BASE_URL = authService.getDomain();
    }

    public ShipmentResponse shipping(ShipmentRequest shipmentRequest) {
        log.info("UPS ShipmentService shipping 실행");
        validation(shipmentRequest);
        final String URL = this.BASE_URL + "/api/shipments/v2409/ship";
        final HttpEntity<ShipWrapRequest> request = new HttpEntity<>(new ShipWrapRequest(shipmentRequest), getHeader());
        final String body = apiCall(URL, HttpMethod.POST, request);

        try {
            ShipWrapResponse shipWrapResponse = om.readValue(body, ShipWrapResponse.class);
            return shipWrapResponse.getShipmentResponse();
        } catch (JsonProcessingException e) {
            log.error("UPS JSON Parsing 실패");
            throw new RuntimeException(e);
        }
    }

    public String cancelShipping(String shipmentidentificationnumber) {
        log.info("UPS ShipmentService cancelShipping 실행");
        Assert.hasText(shipmentidentificationnumber ,"Shipmentidentificationnumber is required");
        final String URL = this.BASE_URL + "/api/shipments/v2409/void/cancel/"+shipmentidentificationnumber;
        final Map<String,Object> requestParam = new HashMap<String, Object>();
        final HttpEntity<Map<String,Object>> request = new HttpEntity<>(requestParam, getHeader());
        return apiCall(BASE_URL, HttpMethod.DELETE, request);
    }

    private String apiCall(String url, HttpMethod method, HttpEntity<?> request) {
        try {
            ResponseEntity<String> stringResponseEntity = restTemplate.exchange(url, method, request, String.class);
            log.info("UPS ShipmentService Response [ data = {} ]", stringResponseEntity.getBody());
            return stringResponseEntity.getBody();
        } catch (HttpClientErrorException e) {
            log.error("UPS ShipmentService 에러 [ Status Code = {} ]",e.getStatusCode());
            HttpHeaders responseHeaders = e.getResponseHeaders();
            if( responseHeaders != null ) {
                List<String> errorCode = responseHeaders.get("Errorcode");
                List<String> errorDescription = responseHeaders.get("Errordescription");
                log.error("UPS ShipmentService Error Code = " + errorCode);
                log.error("UPS ShipmentService Error Description = " + errorDescription);
            }
            throw e;
        }
    }

    private void validation(ShipmentRequest shipmentRequest) {
        Assert.notNull(shipmentRequest,"ShipmentRequest is required");
        Assert.notNull(shipmentRequest.getRequest(),"ShipmentRequest -> Request is required");
        Assert.notNull(shipmentRequest.getShipment(),"ShipmentRequest -> Shipment is required");

        Assert.notNull(shipmentRequest.getRequest().getRequestOption(), "ShipmentRequest -> Request -> RequestOption is required");

        Shipment shipment = shipmentRequest.getShipment();
        Assert.notNull(shipment.getShipper(), "ShipmentRequest -> Shipment -> Shipper is required");
        Assert.notNull(shipment.getShipTo(), "ShipmentRequest -> Shipment -> ShipTo is required");
        Assert.notNull(shipment.getService(), "ShipmentRequest -> Shipment -> Service is required");
        Assert.notEmpty(shipment.getPackages(), "ShipmentRequest -> Shipment -> Package is required");

        Shipper shipper = shipment.getShipper();
        Assert.hasText(shipper.getName(), "ShipmentRequest -> Shipment -> Shipper -> Name is required");
        Assert.hasText(shipper.getShipperNumber(), "ShipmentRequest -> Shipment -> Shipper -> ShipperNumber is required");
        addressValidation(shipper.getAddress(), "ShipmentRequest -> Shipment -> Shipper");

        ShipTo shipTo = shipment.getShipTo();
        Assert.hasText(shipTo.getName(), "ShipmentRequest -> Shipment -> ShipTo -> Name is required");
        addressValidation(shipTo.getAddress(), "ShipmentRequest -> Shipment -> ShipTo");

        ServiceDto service = shipment.getService();
        Assert.notNull(service.getCode(), "ShipmentRequest -> Shipment -> Service -> Code is required");

        List<Packages> packages = shipment.getPackages();
        packages.forEach(packageDto -> {
            Assert.notNull(packageDto.getPackaging(),"ShipmentRequest -> Shipment -> Package -> Packaging is required");
            Assert.notNull(packageDto.getPackaging().getCode(),"ShipmentRequest -> Shipment -> Package -> Packaging -> Code is required");
        });
    }

    private void addressValidation(Address address, String rootLog) {
        Assert.notNull(address, rootLog + "-> Address is required");
        Assert.notEmpty(address.getAddressLine(), rootLog + "-> Address -> AddressLine is required");
        Assert.hasText(address.getCity(), rootLog + "-> Address -> City is required");
        Assert.hasText(address.getCountryCode(), rootLog + "-> Address -> CountryCode is required");
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", authService.getBearerToken());
//        headers.add("transId", "string");
//        headers.add("transactionSrc", "testing");
        return headers;
    }
}
