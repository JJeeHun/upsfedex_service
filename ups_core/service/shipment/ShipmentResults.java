package com.flexfit.comm.svc.ups_core.service.shipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flexfit.comm.svc.ups_core.dto.PackageResult;
import com.flexfit.comm.svc.ups_core.dto.PackageWeight;
import com.flexfit.comm.svc.ups_core.dto.ShipmentCharges;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder @Jacksonized
@Getter @ToString
public class ShipmentResults {

    @JsonProperty("PackageResults")
    private List<PackageResult> packageResults;

    @JsonProperty("ShipmentCharges")
    private ShipmentCharges shipmentCharges;

    @JsonProperty("BillingWeight")
    private PackageWeight billingWeight;

    @JsonProperty("ShipmentIdentificationNumber")
    private String shipmentIdentificationNumber;
}
