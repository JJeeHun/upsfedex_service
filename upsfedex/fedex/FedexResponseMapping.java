package com.flexfit.comm.svc.upsfedex.fedex;

import com.flexfit.comm.svc.fedex_core.dto.*;
import com.flexfit.comm.svc.fedex_core.shipment.CompletedShipmentDetail;
import com.flexfit.comm.svc.fedex_core.shipment.ShipmentResponse;
import com.flexfit.comm.svc.fedex_core.shipment.TransactionShipment;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Fedex 응답정보 Mapping 클래스
 */
public class FedexResponseMapping {
    /**
     * Fedex API 응답 정보를 ShippingList의 Map깩체에 데이터를 주입
     * @param createShipDtos
     * @param requestParams
     */
    public static void shippingListAddResponseAndClientParams(List<CreateShipDto> createShipDtos, Map<String, Object> requestParams) {

        createShipDtos.forEach( createShipDto -> {
            Map<String, Object> request = createShipDto.getRequestMap(); // 요청정보의 객체 참조값
            ShipmentResponse shipmentResponse = createShipDto.getShipmentResponse();

            request.put("WH_ORD_NO", requestParams.get("WH_ORD_NO"));
            request.put("ORD_NO", requestParams.get("ORD_NO"));
            request.put("JOB_G", requestParams.get("JOB_G"));
            request.put("SHIP_AGENT_CD", requestParams.get("SHIP_AGENT_CD"));
            request.put("WORK_USID", requestParams.get("WORK_USID"));

            // 성공
            if (CollectionUtils.isEmpty(shipmentResponse.getErrors())) {
                TransactionShipment transactionShipment = CollectionUtils.firstElement(shipmentResponse.getOutput().getTransactionShipments());
                Alert alert = CollectionUtils.firstElement(shipmentResponse.getOutput().getAlerts());

                if(alert != null) {
                    request.put("STATUS", alert.getAlertType());
                    request.put("CODE", alert.getCode());
                    request.put("MESSAGE", alert.getMessage());
                }

                if( transactionShipment != null )  {
                    CompletedShipmentDetail completedShipmentDetail = transactionShipment.getCompletedShipmentDetail();

                    request.put("CustomerTransactionId", shipmentResponse.getCustomerTransactionId());

                    setSuccessKeyMapping(request);
                    setFedexPieceResponse(request, transactionShipment);
                    setFedexShipmentOperationalDetail(request, completedShipmentDetail);
                    setFedexPackageRateDetail(request, completedShipmentDetail);
                }
            }else {
                request.put("STATUS", "ERROR");
                request.put("CODE", "ERROR");
                request.put("MESSAGE", "ERROR");
                request.put("REQ_BODY", request.toString());
                request.put("RES_BODY", "");
                request.put("IMG_SRC", "");
            }
        });

    }

    private static void setSuccessKeyMapping(Map<String, Object> request) {
        request.put("PACKAGE_COUNT", request.get("PackageCount"));
        request.put("PACKAGE_WEIGHT", request.get("PackageWeight"));
        request.put("DIM_LENGTH", request.get("DIMLength"));
        request.put("DIM_WIDTH", request.get("DIMWidth"));
        request.put("DIM_HEIGHT", request.get("DIMHeight"));
        request.put("CUSTOMER_REFERENCE", request.get("CustomerReferenceValue"));
        request.put("INVOICE_NUMBER", request.get("CustomerInvoiceValue"));
        request.put("P_O_NUMBER", request.get("CustomerPurchaseValue"));
    }

    private static void setFedexPieceResponse(Map<String, Object> request, TransactionShipment transactionShipment) {
        PieceResponse pieceResponse = CollectionUtils.firstElement(transactionShipment.getPieceResponses());

        if( pieceResponse == null ) return;

        PackageDocument packageDocument = CollectionUtils.firstElement(pieceResponse.getPackageDocuments());

        request.put("SHIPMENT_ID", pieceResponse.getTrackingNumber());
        request.put("TRACKING_NUM", pieceResponse.getTrackingNumber());
        request.put("DELIVERY_DATE", pieceResponse.getDeliveryDatestamp());
        request.put("IMG_SRC", packageDocument == null ? null : packageDocument.getEncodedLabel());
    }

    private static void setFedexShipmentOperationalDetail(Map<String, Object> request, CompletedShipmentDetail completedShipmentDetail) {
        OperationalDetail operationalDetail = completedShipmentDetail.getOperationalDetail();
        String deliveryEligibility = CollectionUtils.firstElement(operationalDetail.getDeliveryEligibilities());

        request.put("DELIVERY_DAY",	operationalDetail.getDeliveryDay());
        request.put("TRANSIT_TIME",	operationalDetail.getTransitTime());
        request.put("MONEY_BACK_GUARANTEE", operationalDetail.getIneligibleForMoneyBackGuarantee());
        request.put("DELIVERY_ELIGIBILITIES", deliveryEligibility);

        request.put("SERVICE_CODE", operationalDetail.getServiceCode());
        request.put("PACKAGING_CODE", operationalDetail.getPackagingCode());
    }


    private static void setFedexPackageRateDetail(Map<String, Object> request, CompletedShipmentDetail completedShipmentDetail) {
        CompletedPackageDetail completedPackageDetail = CollectionUtils.firstElement(completedShipmentDetail.getCompletedPackageDetails());

        if( completedPackageDetail == null ) return;

        if (completedPackageDetail.getPackageRating() != null) {
            PackageRateDetails packageRateDetail = CollectionUtils.firstElement(completedPackageDetail.getPackageRating().getPackageRateDetails());

            if(packageRateDetail != null) {
                request.put("BILLING_WEIGHT", packageRateDetail.getBillingWeight().getValue());
                request.put("BASE_CHARGE",packageRateDetail.getBaseCharge());
                request.put("TOTAL_FREIGHT_DISCOUNTS",packageRateDetail.getTotalFreightDiscounts());
                request.put("NET_FREIGHT", packageRateDetail.getNetFreight());
                request.put("TOTAL_SURCHARGE", packageRateDetail.getTotalSurcharges());
                request.put("NET_FEDEX_CHARGE",	packageRateDetail.getNetFedExCharge());	// The package's netFreight + totalSurcharges (not including totalTaxes)
                request.put("TOTAL_TAXES", packageRateDetail.getTotalTaxes());        // Total of the transportation-based taxes.
                request.put("NET_CHARGE", packageRateDetail.getNetCharge());
                request.put("TOTAL_REBATES", packageRateDetail.getTotalRebates());        // The total sum of all rebates applied to this shipment
            }
        }

        // packageOperationDetail
        OperationalDetail packageOperationDetail = completedPackageDetail.getOperationalDetail();
        if (packageOperationDetail != null) {
            List<OperationalInstruction> operationalInstructions = CollectionUtils.isEmpty(packageOperationDetail.getOperationalInstructions()) ? Collections.emptyList() : packageOperationDetail.getOperationalInstructions();
            Barcode barcode = packageOperationDetail.getBarcodes() == null ? null : CollectionUtils.firstElement(packageOperationDetail.getBarcodes().getStringBarcodes());
            String content = operationalInstructions.size() < 8 ? null : operationalInstructions.get(7).getContent();
            request.put("UCC_NO", barcode == null ? null : barcode.getValue());
            request.put("UCC_NO_PRINT", content);
        }
    }
}
