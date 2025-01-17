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
 *   <li><b style="color: #ee6196;">requestOption</b> : nonvalidate<br/>
 *   <li><b style="color: #86da98;">subVersion</b> : Example: 1607 = 2016 July Supported values: 1601, 1607, 1701, 1707, 1801, 1807, 2108, 2205<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Request {

    @JsonProperty("RequestOption")
    private String requestOption;

    @JsonProperty("SubVersion")
    private String subVersion;

    @JsonProperty("TransactionReference")
    private TransactionReference transactionReference;
}
