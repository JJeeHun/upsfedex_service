package com.flexfit.comm.svc.fedex_core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">deliveryDay</b> : 2024-12-11<br/></li>
 *   <li><b style="color: #86da98;">transitTime</b> : ONE_DAY<br/></li>
 *   <li><b style="color: #86da98;">ineligibleForMoneyBackGuarantee</b> : false<br/></li>
 *   <li><b style="color: #86da98;">deliveryEligibilities</b> :  ["SATURDAY_DELIVERY"]<br/></li>
 *   <li><b style="color: #86da98;">ServiceCode</b> : 92<br/></li>
 *   <li><b style="color: #86da98;">PackagingCode</b> : 01<br/></li>
 *   <li><b style="color: #86da98;">barcodes</b> : {@link Barcodes}<br/></li>
 *   <li><b style="color: #86da98;">operationalInstructions</b> : List<{@link OperationalInstruction}><br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class OperationalDetail {
    private String deliveryDay;
    private String transitTime;
    private Boolean ineligibleForMoneyBackGuarantee;
    private List<String> deliveryEligibilities;
    private String ServiceCode;
    private String PackagingCode;
    private Barcodes barcodes;
    private List<OperationalInstruction> operationalInstructions;
}
