package com.flexfit.comm.svc.ups_core.service.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder @Jacksonized
@Getter @ToString
public class ShipWrapResponse {

    @JsonProperty("ShipmentResponse")
    private ShipmentResponse shipmentResponse;
}

