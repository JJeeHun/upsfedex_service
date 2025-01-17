package com.flexfit.comm.svc.ups_core.dto;

import com.flexfit.comm.svc.ups_core.enums.ShipmentChargeType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">type</b> : {@link ShipmentChargeType}<br/>
 *   <li><b style="color: #86da98;">billShipper</b> : {@link BillShipper}<br/>
 *   <li><b style="color: #86da98;">billThirdParty</b> : {@link BillThirdParty}<br/>
 *
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class ShipmentCharge {

    @JsonProperty("Type")
    private ShipmentChargeType type;

    @JsonProperty("BillShipper")
    private BillShipper billShipper;

    @JsonProperty("BillThirdParty")
    private BillThirdParty billThirdParty;
}
