package com.flexfit.comm.svc.ups_core.dto;

import com.flexfit.comm.svc.ups_core.enums.ServiceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">code</b> : {@link ServiceType}<br/>
 *   <li><b style="color: #86da98;">description</b> : Description of the service code. Examples are Next Day Air, Worldwide Express, and Ground.<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class ServiceDto {

    @JsonProperty("Code")
    private ServiceType code;

    @JsonProperty("Description")
    private String description;
}
