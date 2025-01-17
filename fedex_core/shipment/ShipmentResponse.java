package com.flexfit.comm.svc.fedex_core.shipment;

import com.flexfit.comm.svc.fedex_core.dto.ApiResponse;
import com.flexfit.comm.svc.fedex_core.dto.Error;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">transactionId</b> : 624deea6-b709-470c-8c39-4b5511281492<br/></li>
 *   <li><b style="color: #86da98;">customerTransactionId</b> : XXXX_XXX123XXXXX<br/></li>
 *   <li><b style="color: #86da98;">output</b> : {@link ShipmentOutPut}<br/></li>
 *   <li><b style="color: #86da98;">errors</b> : List<{@link Error}><br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class ShipmentResponse extends ApiResponse {
    private String transactionId;
    private String customerTransactionId;
    private ShipmentOutPut output;
    private List<Error> errors;
}
