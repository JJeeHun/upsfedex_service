package com.flexfit.comm.svc.fedex_core.dto;

import com.flexfit.comm.svc.fedex_core.enums.UnitWeightType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">units</b> : {@link UnitWeightType}<br/></li>
 *   <li><b style="color: #ee6196;">value</b> : 10.0<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class BillingWeight {
    @NotNull private UnitWeightType units;
    @NotNull private Double value;
}
