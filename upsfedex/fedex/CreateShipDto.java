package com.flexfit.comm.svc.upsfedex.fedex;

import com.flexfit.comm.svc.fedex_core.enums.ServiceType;
import com.flexfit.comm.svc.fedex_core.enums.USStateCodeType;
import com.flexfit.comm.svc.fedex_core.shipment.ShipmentResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Builder
@Getter @ToString
public class CreateShipDto {

    private ServiceType serviceType;
    private String thirdPartyAccount;
    @Setter private String rootAccount;
    private Double totalWeight;

    private String recipientPerson;
    private String recipientCompany;
    private String recipientPhone;
    private List<String> recipientStreetLines;
    private String recipientCity;
    private USStateCodeType recipientStateOrProvinceCode;
    private String recipientPostalCode;

    private int seq;
    private int packageCount;
    private double packageWeight;
    private int length;
    private int width;
    private int height;
    private String invoice;
    private String po;

    private Boolean isThirdParty;

    /* API 요청 정보 및 응답정보를 객체에 담아주기 위한 용도 ( 기존로직떄문 ) */
    private Map<String,Object> requestMap;
    @Setter private String customerTransactionId;
    @Setter private ShipmentResponse shipmentResponse;

    public boolean isThirdParty() {
        return isThirdParty != null && isThirdParty;
    }
}
