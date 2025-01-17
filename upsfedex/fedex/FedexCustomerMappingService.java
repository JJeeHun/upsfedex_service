package com.flexfit.comm.svc.upsfedex.fedex;

import com.flexfit.comm.svc.fedex_core.auth.OAuthRequest;
import com.flexfit.comm.svc.fedex_core.dto.*;
import com.flexfit.comm.svc.fedex_core.dto.Error;
import com.flexfit.comm.svc.fedex_core.enums.*;
import com.flexfit.comm.svc.fedex_core.shipment.ShipmentService;
import com.flexfit.comm.svc.fedex_core.shipment.CreateShipmentBody;
import com.flexfit.comm.svc.fedex_core.shipment.ShipmentCancelResponse;
import com.flexfit.comm.svc.fedex_core.shipment.ShipmentResponse;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Fedex 내부용 스펙에 맞는 Service
 * 기본정보 하드 코딩 및 내부용 스펙
 */
@Service
@RequiredArgsConstructor
public class FedexCustomerMappingService {
    // 계정 및 운영/테스트 셋팅
    private static final String ID = "";
    private static final String SECRET = "";
    private static final boolean IS_PROD = false;

    // 고정 데이터
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat ("yyyyMMddHHmmssSSS");
    private static final LabelSpecification LABEL_SPECIFICATION = getFixedLabelSpec();
    private static Shipper SHIPPER;
    private static ShippingChargesPayment DEFAULT_SHIPPING_CHARGES_PAYMENT;

    // Fedex 요청 객체
    private final ShipmentService shipmentService;

    public FedexCustomerMappingService() {
        initialize();
        OAuthRequest auth = new OAuthRequest(ID, SECRET, IS_PROD);
        this.shipmentService = new ShipmentService(auth);
    }

    /**
     * Shipper 및 고정 Account를 디비로 가져올 경우 여기서 셋팅
     */
    @PostConstruct
    private void initialize() {
        SHIPPER = getFixedShipper("JASON LEE", "FLEXFIT LLC.", "7144478052",
            Arrays.asList("285 S BERRY ST"), "BREA", "92821");
        DEFAULT_SHIPPING_CHARGES_PAYMENT = getFixedShippingChargePayment(PaymentType.SENDER, "740561073");
    }

    /**
     * 내부 데이터 호출 후 Fedex 호출
     * @param shippings
     * @return 요청정보에 response 및 기타 정보를 담아서 반환
     */
    public List<CreateShipDto> fedexMappingAndCreateShipment(List<Map<String, Object>> shippings) {

        // 1. 내부에서 사용중인 파라미터 객체 타입으로 변환 - Map To CreateShipDto
        List<CreateShipDto> createShipDtos = shippings.stream()
            .map(shipping -> {
                Assert.isTrue("FEDEX".equals(shipping.get("ShipAgentCD")), "Fedex 요청 정보가 아닙니다.");

                double packageWeight = Double.parseDouble((String) shipping.get("PackageWeight"));
                return CreateShipDto.builder()
                    .serviceType(ServiceType.valueOf((String) shipping.get("ShipServiceCD")))
                    .thirdPartyAccount((String) shipping.get("ThirdPartyAccount"))
                    .rootAccount(DEFAULT_SHIPPING_CHARGES_PAYMENT.getPayor().getResponsibleParty().getAccountNumber().getValue())
                    .totalWeight(packageWeight)
                    .recipientPerson((String) shipping.get("ShipToPersonName"))
                    .recipientCompany((String) shipping.get("ShipToCompanyName"))
                    .recipientPhone((String) shipping.get("ShipToPhoneNumber"))
                    .recipientStreetLines(Arrays.asList(
                        (String) shipping.get("ShipToStreetLines1"),
                        (String) shipping.get("ShipToStreetLines2"))
                    )
                    .recipientCity((String) shipping.get("ShipToCity"))
                    .recipientStateOrProvinceCode(USStateCodeType.valueOf((String) shipping.get("ShipToStateProvinceCode")))
                    .recipientPostalCode((String) shipping.get("ShipToPostalCode"))
                    .seq(((Long) shipping.get("SEQ")).intValue())
                    .packageCount(Integer.parseInt((String) shipping.get("PackageCount")))
                    .packageWeight(packageWeight)
                    .length(Integer.parseInt((String) shipping.get("DIMLength")))
                    .width(Integer.parseInt((String) shipping.get("DIMWidth")))
                    .height(Integer.parseInt((String) shipping.get("DIMHeight")))
                    .invoice((String) shipping.get("CustomerInvoiceValue"))
                    .po((String) shipping.get("CustomerPurchaseValue"))
                    .isThirdParty("3RD PARTY".equals(shipping.get("ShipMthdCD")))
                    .requestMap(shipping) // 요청정보 객체 참조[Map] 객체를 담아줌
                    .build();
            }).collect(Collectors.toList());

        // 2. Fedex Api Call
        fedexCreateShipment(createShipDtos);

        return createShipDtos;
    }

    /**
     * Fedex 요청 ( 내부 스펙 CreateShipDto 를 제외한 파라미터는 하드코딩 )
     * @param shippings - 내부생성정보 dto
     * @return
     */
    private void fedexCreateShipment(List<CreateShipDto> shippings) {
        MasterTrackingId masterTrackingId = null;
        String customerTransactionId = DATE_FORMAT.format(new Date());

        for(CreateShipDto shipping : shippings) {
            CreateShipmentBody createShipmentBody = getShipmentBody(shipping, masterTrackingId);
            ShipmentResponse shipmentResponse = null;
            // 실패시에도 저장해야하기 때문에 try catch ( 이중 출고 문제 )
            try {
                shipmentResponse = shipmentService.createShipment(customerTransactionId, createShipmentBody);
            } catch(Exception e) {
                shipmentResponse = ShipmentResponse.builder()
                    .errors(Arrays.asList(Error.builder()
                            .code("Error")
                            .message("Error")
                        .build()))
                    .build();
            }

            shipping.setCustomerTransactionId(customerTransactionId);
            shipping.setShipmentResponse(shipmentResponse);

            // 첫 요청의 응답 masterTrackingId를 계속 넘겨줌
            if( masterTrackingId == null ) {
                if(shipmentResponse.getOutput() != null) {
                    masterTrackingId = shipmentResponse.getOutput().getTransactionShipments().get(0)
                        .getCompletedShipmentDetail()
                        .getMasterTrackingId();
                }
            }
        };
    }

    public ShipmentCancelResponse cancelShipment(String trackingNumber) {
        String accountNumber = DEFAULT_SHIPPING_CHARGES_PAYMENT.getPayor().getResponsibleParty().getAccountNumber().getValue();
        return shipmentService.cancelShipment(accountNumber, trackingNumber);
    }

    /**
     * Fedex CreateShipment Body 생성
     * - 내부에서 사용하는 파라미터를 Fedex 대응 객체로 변환
     * @param shipping
     * @param masterTrackingId
     * @return
     */
    private static CreateShipmentBody getShipmentBody(CreateShipDto shipping, MasterTrackingId masterTrackingId) {
        ShippingChargesPayment shippingChargesPayment = shipping.isThirdParty()
            ? getFixedShippingChargePayment(PaymentType.THIRD_PARTY, shipping.getThirdPartyAccount()) : DEFAULT_SHIPPING_CHARGES_PAYMENT;

        return CreateShipmentBody.builder()
            .accountNumber(DEFAULT_SHIPPING_CHARGES_PAYMENT.getPayor().getResponsibleParty().getAccountNumber())
            .labelResponseOptions(LabelResponseOptionType.LABEL)
            .oneLabelAtATime(true)
            .requestedShipment(RequestedShipment.builder()
                .shipDatestamp(LocalDate.now().toString())
                .serviceType(shipping.getServiceType())
                .packagingType(PackagingType.YOUR_PACKAGING)
                .pickupType(PickupType.USE_SCHEDULED_PICKUP)    // TODO - 필수 [기존에 없는 듯 ]
                .totalWeight(shipping.getTotalWeight())         // TODO - 필수 없는 듯
                .totalPackageCount(shipping.getPackageCount())
                .shipper(SHIPPER)
                .shippingChargesPayment(shippingChargesPayment) // TODO - 서드파티일 경우 객체 새로 생성
                .labelSpecification(LABEL_SPECIFICATION)
                .recipients(Arrays.asList(
                    Recipient.builder()
                        .contact(Contact.builder()
                            .personName(shipping.getRecipientPerson())
                            .companyName(shipping.getRecipientCompany())
                            .phoneNumber(shipping.getRecipientPhone()) // max 넘어감
                            .build())
                        .address(Address.builder()
                            .streetLines(shipping.getRecipientStreetLines())
                            .city(shipping.getRecipientCity())
                            .stateOrProvinceCode(shipping.getRecipientStateOrProvinceCode())
                            .postalCode(shipping.getRecipientPostalCode())
                            .countryCode(CountryCodeType.US)
                            .residential(ServiceType.GROUND_HOME_DELIVERY.equals(shipping.getServiceType()))
                            .build())
                        .build()
                ))
                .requestedPackageLineItems(Arrays.asList(
                    getRequestedPackageLineItem(shipping.getSeq(),
                        shipping.getPackageWeight(),
                        shipping.getLength(),
                        shipping.getWidth(),
                        shipping.getHeight(),
                        shipping.getInvoice(),
                        shipping.getPo()
                    )
                ))
                .masterTrackingId(masterTrackingId) // TODO - 여러건일 경우 부모의 응답 값인 MasterTrackingId를 setting
                .build())
            .build();
    }

    private static RequestedPackageLineItem getRequestedPackageLineItem(int seq,
                                                                        double weight,
                                                                        int length,
                                                                        int width,
                                                                        int height,
                                                                        String invoice,
                                                                        String poNumber
    ) {
        return RequestedPackageLineItem.builder()
            .sequenceNumber(seq)
            .weight(Weight.builder()
                .units(UnitWeightType.LB)
                .value(weight)
                .build())
            .dimensions(Dimensions.builder()
                .units(UnitLengthType.IN)
                .length(length)
                .width(width)
                .height(height)
                .build())
            .customerReferences(Arrays.asList(
                CustomerReference.builder()
                    .customerReferenceType(CustomerReferenceType.CUSTOMER_REFERENCE)
                    .value("Flexfit Merchandise")
                    .build(),
                CustomerReference.builder()
                    .customerReferenceType(CustomerReferenceType.INVOICE_NUMBER)
                    .value(invoice)
                    .build(),
                CustomerReference.builder()
                    .customerReferenceType(CustomerReferenceType.P_O_NUMBER)
                    .value(poNumber)
                    .build()
            ))
            .build();
    }

    private static LabelSpecification getFixedLabelSpec() {
        return LabelSpecification.builder()
            .labelFormatType(LabelFormatType.COMMON2D)
            .imageType(ImageType.PNG)
            .labelStockType(LabelStockType.PAPER_4X6)
            .build();
    }

    private static ShippingChargesPayment getFixedShippingChargePayment(PaymentType paymentType, String accountNumber) {
        return ShippingChargesPayment.builder()
            .paymentType(paymentType)
            .payor(Payor.builder()
                .responsibleParty(ResponsibleParty.builder()
                    .accountNumber(AccountNumber.builder()
                        .value(accountNumber)
                        .build())
                    .build())
                .build())
            .build();
    }

    private static Shipper getFixedShipper(
        String person, String company, String phone,
        List<String> streetLines, String city, String postalCode
    ) {
        return Shipper.builder()
            .contact(Contact.builder() // TODO - 하드코딩
                .personName(person)
                .companyName(company)
                .phoneNumber(phone)
                .build())
            .address(Address.builder() // TODO - 하드코딩
                .streetLines(streetLines)
                .city(city)
                .stateOrProvinceCode(USStateCodeType.CA)
                .postalCode(postalCode)
                .countryCode(CountryCodeType.US)
                .build())
            .build();
    }
}
