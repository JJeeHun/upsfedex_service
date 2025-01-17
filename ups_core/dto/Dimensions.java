package com.flexfit.comm.svc.ups_core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">unitOfMeasurement</b> : {@link CommonCode}<br/>
 *   <li><b style="color: #ee6196;">length</b> : Package length. Length must be the longest dimension of the container. Valid values are 0 to 108 IN and 0 to 270 CM<br/>
 *   <li><b style="color: #ee6196;">width</b> : Package width.<br/>
 *   <li><b style="color: #ee6196;">height</b> : Package height.<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Dimensions {

    @JsonProperty("UnitOfMeasurement")
    private CommonCode unitOfMeasurement;

    @JsonProperty("Length")
    private String length;

    @JsonProperty("Width")
    private String width;

    @JsonProperty("Height")
    private String height;
}
