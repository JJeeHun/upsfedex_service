package com.flexfit.comm.svc.ups_core.service.shipment;


import com.flexfit.comm.svc.ups_core.dto.Response;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flexfit.comm.svc.ups_core.dto.TransactionReference;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder @Jacksonized
@Getter @ToString
public class ShipmentResponse {

    @JsonProperty("Response")
    private Response response;

    @JsonProperty("ShipmentResults")
    private ShipmentResults shipmentResults;

    @JsonProperty("TransactionReference")
    private TransactionReference transactionReference;
}
