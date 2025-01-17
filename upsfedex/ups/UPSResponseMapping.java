package com.flexfit.comm.svc.upsfedex.ups;

import com.flexfit.comm.svc.ups_core.dto.CommonCode;
import com.flexfit.comm.svc.ups_core.dto.PackageResult;
import com.flexfit.comm.svc.ups_core.dto.PackageWeight;
import com.flexfit.comm.svc.ups_core.dto.ShipmentCharges;
import com.flexfit.comm.svc.ups_core.enums.ServiceType;
import com.flexfit.comm.svc.ups_core.service.shipment.ShipmentResponse;
import com.flexfit.comm.svc.ups_core.service.shipment.ShipmentResults;

import java.util.List;
import java.util.Map;

public class UPSResponseMapping {

    public static void shippingListAddResponseAndClientParams(Map<String, Object> requestParams,
                                                              List<Map<String, Object>> shippingList,
                                                              ShipmentResponse shipmentResponse) {
        for (int i=0; i < shippingList.size(); i++) {
            Map<String, Object> ship = shippingList.get(i);
            ship.put("WH_ORD_NO", requestParams.get("WH_ORD_NO"));
            ship.put("ORD_NO", requestParams.get("ORD_NO"));
            ship.put("JOB_G", requestParams.get("JOB_G"));
            ship.put("SHIP_AGENT_CD", requestParams.get("SHIP_AGENT_CD"));
            ship.put("WORK_USID", requestParams.get("WORK_USID"));

            if (shipmentResponse == null) {
                ship.put("STATUS", "ERROR");
                ship.put("CODE", "ERROR");
                ship.put("MESSAGE", "ERROR");
                continue;
            }

            if(shipmentResponse.getTransactionReference() != null) {
            	ship.put("CustomerTransactionId", shipmentResponse.getTransactionReference().getCustomerContext());
            }

            CommonCode responseStatus = shipmentResponse.getResponse().getResponseStatus();
            ship.put("STATUS", "SUCCESS");
            ship.put("CODE", responseStatus.getCode());
            ship.put("MESSAGE", responseStatus.getDescription());

            ShipmentResults shipmentResults = shipmentResponse.getShipmentResults();
            setCommonInfo(ship, shippingList.size(), shipmentResults.getShipmentCharges(), shipmentResults.getBillingWeight());
            setPackageResult(ship, shipmentResults.getPackageResults().get(i));
        }

    }
    private static void setCommonInfo(Map<String, Object> ship,
                                      int packageCount,
                                      ShipmentCharges shipmentCharges,
                                      PackageWeight billingWeight) {
        ship.put("PACKAGE_COUNT", packageCount);
        ship.put("PACKAGE_WEIGHT", ship.get("PackageWeight"));
        ship.put("DIM_LENGTH", ship.get("DIMLength"));
        ship.put("DIM_WIDTH", ship.get("DIMWidth"));
        ship.put("DIM_HEIGHT", ship.get("DIMHeight"));
        ship.put("CUSTOMER_REFERENCE", ship.get("CustomerReferenceValue"));
        ship.put("INVOICE_NUMBER", ship.get("CustomerInvoiceValue"));
        ship.put("P_O_NUMBER", ship.get("CustomerPurchaseValue"));

        ship.put("SERVICE_CODE", ship.get("ShipServiceCD"));
        ship.put("PACKAGING_CODE", ServiceType.SECOND_DAY_AIR.getCode()); // 고정
        ship.put("BILLING_WEIGHT", billingWeight.getWeight());
        ship.put("TOTAL_TRANSPORT_CHARGES", shipmentCharges.getTransportationCharges().getMonetaryValue());
        ship.put("TOTAL_SERVICE_CHARGES", shipmentCharges.getServiceOptionsCharges().getMonetaryValue());
        ship.put("TOTAL_CHARGES", shipmentCharges.getTotalCharges().getMonetaryValue());
    }

    private static void setPackageResult(Map<String, Object> ship, PackageResult packageResults) {
        ship.put("TRACKING_NUM", packageResults.getTrackingNumber()); // shipmentidentificationnumber를 넣어야 할지도?
        ship.put("SERVICE_OPTION_CHARGES", packageResults.getServiceOptionsCharges().getMonetaryValue());
        ship.put("IMG_SRC", packageResults.getShippingLabel().getGraphicImage());
    }
}
