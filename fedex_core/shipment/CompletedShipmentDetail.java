package com.flexfit.comm.svc.fedex_core.shipment;

import com.flexfit.comm.svc.fedex_core.dto.CompletedPackageDetail;
import com.flexfit.comm.svc.fedex_core.dto.MasterTrackingId;
import com.flexfit.comm.svc.fedex_core.dto.OperationalDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #86da98;">masterTrackingId</b> : {@link MasterTrackingId}<br/></li>
 *   <li><b style="color: #86da98;">operationalDetail</b> : {@link OperationalDetail}<br/></li>
 *   <li><b style="color: #86da98;">completedPackageDetails</b> : List<{@link CompletedPackageDetail}><br/></li>
 * </ul>
 */
@Builder @Jacksonized
@Getter @ToString
public class CompletedShipmentDetail {

    private MasterTrackingId masterTrackingId;
    private OperationalDetail operationalDetail;
    private List<CompletedPackageDetail> completedPackageDetails;
}
