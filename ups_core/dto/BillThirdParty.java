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
 *   <li><b style="color: #ee6196;">accountNumber</b> : The UPS account number of the third party shipper. The account must be a valid UPS account number that is active.
 *
 * For US, PR and CA accounts, the account must be either a daily pickup account, an occasional account, or a customer B.I.N account, or a drop shipper account.
 *
 * All other accounts must be either a daily pickup account, an occasional account, a drop shipper account, or a non-shipping account.<br/>
 *   <li><b style="color: #86da98;">address</b> : {@link Address}<br/>
 *
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class BillThirdParty {

    @JsonProperty("AccountNumber")
    private String accountNumber;

    @JsonProperty("Address")
    private Address address;
}
