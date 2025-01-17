package com.flexfit.comm.svc.ups_core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">shipmentCharge</b> : List<{@link ShipmentCharge}><br/>
 *
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class PaymentInformation {

    @JsonProperty("ShipmentCharge")
    private List<ShipmentCharge> shipmentCharge;
}
