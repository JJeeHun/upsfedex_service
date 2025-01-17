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
 *   <li><b style="color: #ee6196;">number</b> :
 *      Shipper's phone Number. Valid values are 0 - 9.
 *      If Shipper country or territory is US, PR, CA, and VI, the layout is: area code, 7 digit PhoneNumber or area code, 7 digit PhoneNumber, 4 digit extension number.
 *      For other countries or territories, the layout is: country or territory code, area code, 7 digit number.
 *      A phone number is required if destination is international.<br/>
 *   <li><b style="color: #86da98;">extension</b> : Shipper's phone extension.<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Phone {
    @JsonProperty("Number")
    private String number;

    @JsonProperty("Extension")
    private String extension;
}
