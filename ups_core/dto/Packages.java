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
 *   <li><b style="color: #86da98;">description</b> : Merchandise description of package. Required for shipment with return service.<br/>
 *   <li><b style="color: #ee6196;">packaging</b> : {@link CommonCode}<br/>
 *   <li><b style="color: #86da98;">dimensions</b> : {@link Dimensions}<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Packages {

    @JsonProperty("Description")
    private String description;

    @JsonProperty("Packaging")
    private CommonCode packaging;

    @JsonProperty("Dimensions")
    private Dimensions dimensions;

    @JsonProperty("PackageWeight")
    private PackageWeight packageWeight;
}
