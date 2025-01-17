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
 *   <li><b style="color: #86da98;">locationID</b> : Location ID is a unique identifier referring to a specific shipping/receiving location. Location ID must be alphanumeric characters. All letters must be capitalized.<br/>
 * </ul>
 */
@SuperBuilder @Jacksonized
@Getter @ToString
public class ShipTo extends Company {

    @JsonProperty("LocationID")
    private String locationID;
}
