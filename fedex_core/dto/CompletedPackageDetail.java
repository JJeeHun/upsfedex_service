package com.flexfit.comm.svc.fedex_core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">packageRating</b> : {@link PackageRating}<br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class CompletedPackageDetail {
    private PackageRating packageRating;
    private OperationalDetail operationalDetail;
}
