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
 *   <li><b style="color: #86da98;">accountNumber</b> : UPS account number. Must be the same UPS account number as the one provided in Shipper/ShipperNumber.
 *
 * Either this element or one of the sibling elements CreditCard or AlternatePaymentMethod must be provided, but all of them may not be provided.<br/>
 *
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class BillShipper {
    @JsonProperty("AccountNumber")
    private String accountNumber;
}
