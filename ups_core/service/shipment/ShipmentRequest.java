package com.flexfit.comm.svc.ups_core.service.shipment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flexfit.comm.svc.ups_core.dto.LabelSpecification;
import com.flexfit.comm.svc.ups_core.dto.Request;
import com.flexfit.comm.svc.ups_core.dto.Shipment;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">request</b> : {@link Request}<br/>
 *   <li><b style="color: #ee6196;">shipment</b> : {@link Shipment}<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShipmentRequest {

    @JsonProperty("Request")
    private Request request;

    @JsonProperty("Shipment")
    private Shipment shipment;

    @JsonProperty("LabelSpecification")
    private LabelSpecification labelSpecification;
}
