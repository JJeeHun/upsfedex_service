package com.flexfit.comm.svc.fedex_core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">billingWeight</b> : {@link BillingWeight}<br/></li>
 *   <li><b style="color: #86da98;">totalFreightDiscounts</b> : 44.55<br/></li>
 *   <li><b style="color: #86da98;">totalTaxes</b> : 3.45<br/></li>
 *   <li><b style="color: #86da98;">baseCharge</b> : 45.67<br/></li>
 *   <li><b style="color: #86da98;">totalRebates</b> : 4.56<br/></li>
 *   <li><b style="color: #86da98;">netFreight</b> : 4.89<br/></li>
 *   <li><b style="color: #86da98;">totalSurcharges</b> : 22.56<br/></li>
 *   <li><b style="color: #86da98;">netFedExCharge</b> : 12.56<br/></li>
 *   <li><b style="color: #86da98;">netCharge</b> : 121.56<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class PackageRateDetails {
    private BillingWeight billingWeight;
    private Double totalFreightDiscounts;
    private Double totalTaxes;
    private Double baseCharge;
    private Double totalRebates;
    private Double netFreight;
    private Double totalSurcharges;
    private Double netFedExCharge;
    private Double netCharge;
}
