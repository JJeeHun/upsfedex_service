package com.flexfit.comm.svc.fedex_core.dto;

import com.flexfit.comm.svc.fedex_core.enums.LabelFormatType;
import com.flexfit.comm.svc.fedex_core.enums.LabelStockType;
import javax.validation.constraints.NotNull;

import com.flexfit.comm.svc.fedex_core.enums.ImageType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

// TODO - Color Info [ required: #ee6196, option: #86da98, link: #4883f4 ]
/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">labelFormatType</b> : {@link LabelFormatType}<br/></li>
 *   <li><b style="color: #ee6196;">labelStockType</b> : {@link LabelStockType}<br/></li>
 *   <li><b style="color: #ee6196;">imageType</b> : {@link ImageType}<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class LabelSpecification {

    @NotNull
    private LabelFormatType labelFormatType;

    @NotNull
    private LabelStockType labelStockType;

    @NotNull
    private ImageType imageType;
}
