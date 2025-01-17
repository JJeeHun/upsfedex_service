package com.flexfit.comm.svc.fedex_core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">type</b> : COMMON_2D<br/></li>
 *   <li><b style="color: #86da98;">value</b> : Wyk+HjAxHTAyOTIwODEd<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Barcode {
    private String type;
    private String value;
}
