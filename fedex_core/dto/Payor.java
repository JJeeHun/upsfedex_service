package com.flexfit.comm.svc.fedex_core.dto;


import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <h2>Payor [PaymentType가 Sender가 아닐 경우 필수] </h2>
 *
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">responsibleParty</b> : {@link ResponsibleParty}<br/>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Payor {

    @NotNull
    private ResponsibleParty responsibleParty;
}
