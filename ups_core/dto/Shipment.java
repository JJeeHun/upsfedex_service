package com.flexfit.comm.svc.ups_core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">description</b> : The Description of Goods for the shipment. Applies to international and domestic shipments.
 *
 * Provide a detailed description of items being shipped for documents and non-documents.
 *
 * Examples: "annual reports" and "9 mm steel screws". Required if all of the listed conditions are true: ShipFrom and ShipTo countries or territories are not the same; The packaging type is not UPS Letter; The ShipFrom and or ShipTo countries or territories are not in the European Union or the ShipFrom and ShipTo countries or territories are both in the European Union and the shipments service type is not UPS Standard.<br/>
 *   <li><b style="color: #ee6196;">shipper</b> : {@link Shipper}<br/>
 *   <li><b style="color: #ee6196;">shipTo</b> : {@link ShipTo}<br/>
 *   <li><b style="color: #ee6196;">service</b> : {@link ServiceDto}<br/>
 *   <li><b style="color: #ee6196;">packages</b> : List<{@link Packages}><br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Shipment {
    @JsonProperty("Description")
    private String description;

    @JsonProperty("Shipper")
    private Shipper shipper;

    @JsonProperty("ShipTo")
    private ShipTo shipTo;

    @JsonProperty("Service")
    private ServiceDto service;

    @JsonProperty("Package")
    private List<Packages> packages;

    @JsonProperty("PaymentInformation")
    private PaymentInformation paymentInformation;
}
