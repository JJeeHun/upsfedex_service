package com.flexfit.comm.svc.upsfedex.ups;

import com.flexfit.comm.svc.ups_core.dto.*;
import com.flexfit.comm.svc.ups_core.enums.ServiceType;
import com.flexfit.comm.svc.ups_core.enums.ShipmentChargeType;
import com.flexfit.comm.svc.ups_core.service.shipment.ShipmentRequest;
import com.flexfit.comm.svc.ups_core.service.shipment.ShipmentResponse;
import com.flexfit.comm.svc.ups_core.service.shipment.ShipmentService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UPSShipService {

    // 계정 및 운영/테스트 정보
    private final String CLIENT_ID = "";
    private final String SECRET = "";
    private final String ACCOUNT = "";
    private final boolean IS_PROD = false;

    // 고정 데이터 셋팅
    private final CommonCode FIXED_PACKAGING = CommonCode.builder().code("02").description("Customer Supplied Package").build();
    private final CommonCode FIXED_LENGTH =  CommonCode.builder().code("IN").description("Inches").build();
    private final CommonCode FIXED_WEIGHT = CommonCode.builder().code("LBS").description("Pounds").build();
    private final Shipper FIXED_SHIPPER = getFixedShipper();
    private final LabelSpecification FIXED_LABEL_SPECIFICATION = getFixedLabelSpecification();
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat ("yyyyMMddHHmmssSSS");

    // UPS 호출 객체 셋팅
    private final ShipmentService SHIPMENT_SERVICE = new ShipmentService(CLIENT_ID, SECRET, ACCOUNT, IS_PROD);

    public ShipmentResponse shipment(Map<String,Object> requestParam, List<Map<String,Object>> searchShipment) {
        Assert.notEmpty(searchShipment, "비어있을수 없습니다.");
        final Map<String, Object> FIRST_SHIPMENT = CollectionUtils.firstElement(searchShipment);

        final ShipmentRequest SHIPMENT_REQUEST = ShipmentRequest.builder()
            .request(getRequest())
            .shipment(getShipment(searchShipment, FIRST_SHIPMENT))
            .labelSpecification(this.FIXED_LABEL_SPECIFICATION)
            .build();

        // 요청 실패시 null을 return ( 이중출고 문제 )
        try {
            return SHIPMENT_SERVICE.shipping(SHIPMENT_REQUEST);
        } catch (Exception e) {
            return null;
        }
    }

    public String cancelShipment(String shipmentidentificationnumber) {
        return SHIPMENT_SERVICE.cancelShipping(shipmentidentificationnumber);
    }

    private Shipment getShipment(List<Map<String, Object>> searchShipment, Map<String, Object> firstShipment) {
        return Shipment.builder()
            .shipper(this.FIXED_SHIPPER)
            .shipTo(getShipTo(firstShipment))
            .service(getService(firstShipment))
            .packages(getPackages(searchShipment))
            .paymentInformation(getPaymentInformation(firstShipment))
            .build();
    }

    private PaymentInformation getPaymentInformation(Map<String, Object> firstShipment) {
        ShipmentCharge shipmentCharge = ShipmentCharge.builder()
            .type(ShipmentChargeType.TRANSPORTATION)
            .billShipper(BillShipper.builder()
                .accountNumber(this.ACCOUNT) // TODO - oAuth account
                .build())
            .build();

        if("3RD PARTY".equals(firstShipment.get("ShipMthdCD"))) {
            shipmentCharge = ShipmentCharge.builder()
                .type(ShipmentChargeType.TRANSPORTATION)
                .billThirdParty(BillThirdParty.builder()
                    .accountNumber((String) firstShipment.get("ThirdPartyAccount"))
                    .address(Address.builder()
                        .postalCode((String) firstShipment.get("ShipToPostalCode"))
                        .countryCode((String) firstShipment.get("ShipToCountryCode"))
                        .build())
                    .build())
                .build();
        }

        return PaymentInformation.builder()
            .shipmentCharge(Arrays.asList(shipmentCharge))
            .build();
    }

    private List<Packages> getPackages(List<Map<String, Object>> firstShipment) {
        return firstShipment.stream()
            .map(shipment -> Packages.builder()
                .description((String) shipment.get("PackageDescription"))
                .packaging(this.FIXED_PACKAGING)
                .dimensions(Dimensions.builder()
                    .unitOfMeasurement(this.FIXED_LENGTH)
                    .length((String) shipment.get("DIMLength"))
                    .width((String) shipment.get("DIMWidth"))
                    .height((String) shipment.get("DIMHeight"))
                    .build())
                .packageWeight(PackageWeight.builder()
                    .unitOfMeasurement(this.FIXED_WEIGHT)
                    .weight((String) shipment.get("PackageWeight"))
                    .build())
                .build())
            .collect(Collectors.toList());
    }

    private ServiceDto getService(Map<String, Object> firstShipment) {
        return ServiceDto.builder()
            .code(ServiceType.fromCode((String) firstShipment.get("ShipServiceCD")))
            .description((String) firstShipment.get("ShipServiceDESC"))
            .build();
    }

    private ShipTo getShipTo(Map<String, Object> firstShipment) {
        return ShipTo.builder()
            .name((String) firstShipment.get("ShipToPersonName"))
            .companyDisplayableName((String) firstShipment.get("ShipToCompanyName"))
            .phone(Phone.builder()
                .number((String) firstShipment.get("ShipToPhoneNumber"))
                .build())
            .address(Address.builder()
                .addressLine(Arrays.asList((String) firstShipment.get("ShipToStreetLines1"), (String) firstShipment.get("ShipToStreetLines2")))
                .city((String) firstShipment.get("ShipToCity"))
                .stateProvinceCode((String) firstShipment.get("ShipToStateProvinceCode"))
                .postalCode((String) firstShipment.get("ShipToPostalCode"))
                .countryCode((String) firstShipment.get("ShipToCountryCode"))
                .build())
            .build();
    }

    private Request getRequest() {
        final String TODAY = DATE_FORMAT.format(new Date());

        return Request.builder()
            .requestOption("validate")
            .transactionReference(TransactionReference.builder()
                .customerContext(TODAY)
                .build())
            .build();
    }

    private Shipper getFixedShipper() {
        return Shipper.builder()
            .name("JASON LEE")
            .companyDisplayableName("FLEXFIT LLC.")
            .taxIdentificationNumber("81-4412988")
            .phone(Phone.builder()
                .number("7144478052")
                .build())
            .shipperNumber(this.ACCOUNT)
            .address(Address.builder()
                .addressLine(Arrays.asList("625 COLUMBIA ST"))
                .city("BREA")
                .stateProvinceCode("CA")
                .postalCode("92821")
                .countryCode("US")
                .build())
            .build();
    }

    private LabelSpecification getFixedLabelSpecification() {
        return LabelSpecification.builder()
            .labelImageFormat(CommonCode.builder()
                .code("PNG")
                .description("PNG")
                .build())
            .labelStockSize(LabelStockSize.builder()
                .height("6")
                .width("4")
                .build())
            .build();
    }
}
