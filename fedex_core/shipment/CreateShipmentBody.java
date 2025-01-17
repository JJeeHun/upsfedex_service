package com.flexfit.comm.svc.fedex_core.shipment;

import com.flexfit.comm.svc.fedex_core.dto.AccountNumber;
import com.flexfit.comm.svc.fedex_core.dto.RequestedShipment;
import com.flexfit.comm.svc.fedex_core.enums.LabelResponseOptionType;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;


// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">requestedShipment</b> : {@link RequestedShipment}<br/></li>
 *   <li><b style="color: #ee6196;">labelResponseOptions</b> : {@link LabelResponseOptionType}<br/></li>
 *   <li><b style="color: #ee6196;">accountNumber</b> : {@link AccountNumber}<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class CreateShipmentBody {
    @NotNull
    private RequestedShipment requestedShipment;

    @NotNull
    private LabelResponseOptionType labelResponseOptions;

    @NotNull
    private AccountNumber accountNumber;

    private boolean oneLabelAtATime;

}
