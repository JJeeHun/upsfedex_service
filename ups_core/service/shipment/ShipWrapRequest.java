package com.flexfit.comm.svc.ups_core.service.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ShipWrapRequest {

    @JsonProperty("ShipmentRequest")
    private final ShipmentRequest shipmentRequest;
}

