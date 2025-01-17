package com.flexfit.comm.svc.fedex_core.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <b style="color:#5DADEC">Fedex 제공 스펙 X, 편의 속성 정의</b>
 * <br/>
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">requestJson</b> : "{key:value}"<br/></li>
 *   <li><b style="color: #86da98;">responseJson</b> : "{key:value}"<br/></li>
 * </ul>
 */
@Getter @Setter
public class ApiResponse {
    private String requestJson;
    private String responseJson;
}
