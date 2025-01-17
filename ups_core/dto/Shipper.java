package com.flexfit.comm.svc.ups_core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">shipperNumber</b> : Company Name 1~35<br/>
 * </ul>
 */
@SuperBuilder @Jacksonized
@Getter @ToString
public class Shipper extends Company {

    @JsonProperty("ShipperNumber")
    private String shipperNumber;
}
