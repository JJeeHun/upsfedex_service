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
 *   <li><b style="color: #86da98;">code</b> : Package dimensions measurement code. Valid codes: IN = Inches CM = Centimeters 00 = Metric Units Of Measurement 01 = English Units of Measurement The unit of measurement must be valid for the Shipper country or territory.
 *   <li><b style="color: #86da98;">description</b> : Description of the package dimensions measurement units.<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class CommonCode {
    @JsonProperty("Code")
    private String code;

    @JsonProperty("Description")
    private String description;
}
