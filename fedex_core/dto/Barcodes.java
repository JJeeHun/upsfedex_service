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
 *   <li><b style="color: #86da98;">binaryBarcodes</b> : List<{@link Barcode}><br/></li>
 *   <li><b style="color: #86da98;">stringBarcodes</b> : List<{@link Barcode}><br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class Barcodes {
    private List<Barcode> binaryBarcodes;
    private List<Barcode> stringBarcodes;
}
