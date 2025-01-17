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
 *   <li><b style="color: #ee6196;">weight</b> : Packages weight. Weight accepted for letters/envelopes. Only average package weight is required for Ground Freight Pricing Shipment.<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class PackageWeight {
    @JsonProperty("UnitOfMeasurement")
    private CommonCode unitOfMeasurement;

    @JsonProperty("Weight")
    private String weight;
}
