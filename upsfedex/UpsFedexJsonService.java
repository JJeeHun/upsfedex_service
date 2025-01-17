package com.flexfit.comm.svc.upsfedex;

import com.flexfit.comm.svc.UpsFedexService;
import com.flexfit.comm.svc.fedex_core.shipment.ShipmentCancelResponse;
import com.flexfit.comm.svc.impl.UpsFedexDao;
import com.flexfit.comm.svc.ups_core.service.shipment.ShipmentResponse;
import com.flexfit.comm.svc.upsfedex.fedex.CreateShipDto;
import com.flexfit.comm.svc.upsfedex.fedex.FedexCustomerMappingService;
import com.flexfit.comm.svc.upsfedex.fedex.FedexResponseMapping;
import com.flexfit.comm.svc.upsfedex.ups.UPSResponseMapping;
import com.flexfit.comm.svc.upsfedex.ups.UPSShipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service("UpsFedexJsonService")
@RequiredArgsConstructor
public class UpsFedexJsonService implements UpsFedexService {
    private final UpsFedexDao upsFedexDao;
    private final FedexCustomerMappingService fedexService;
    private final UPSShipService upsShipService;

    @Transactional
    public List<Map<String, Object>> UPS_FEDEX_SHIPPING(Map<String, Object> params) {

        // 1. Log 조회
        List<Map<String, Object>> logExist = upsFedexDao.SHIPPING_LOG_SEARCH(params);

        if(!CollectionUtils.isEmpty(logExist)) return logExist; // 이력에 존재 할 경우

        // 2. Shipping 조회
        final List<Map<String, Object>> shippingList = upsFedexDao.UPS_FEDEX(params);

        ShipAgentCodeType shipAgentCd = ShipAgentCodeType.valueOf((String) params.get("SHIP_AGENT_CD"));

        // 3-1. Fedex API 요첨
        if(ShipAgentCodeType.FEDEX.equals(shipAgentCd)) {
            // 1. Fedex API CALL
            List<CreateShipDto> createShipDtos = fedexService.fedexMappingAndCreateShipment(shippingList);
            // 2. DB에 저장 할 요청정보 Setting - shippingList의 map에 client params setting
            FedexResponseMapping.shippingListAddResponseAndClientParams(createShipDtos, params);
        }
        // 3-2. UPS API 요청
        else {
            // 1. UPS API CALL
            ShipmentResponse shipment = upsShipService.shipment(params, shippingList);
            // 2. ShippingList에 Log에 필요한 정보 저장
            UPSResponseMapping.shippingListAddResponseAndClientParams(params, shippingList, shipment);
        }

        // 4. Shipping 정보 DB에 저장 - response 데이터 및 client param가 적용된 shipping 정보를 log에 저장
        for(int i=0; i<shippingList.size(); i++) {
        	shippingList.get(i).put("SEQ", i+1);
        	upsFedexDao.UPS_FEDEX_SHIPMENT_SAVE_LOG(shippingList.get(i));
        }

        return shippingList;
    }

    @Override
    public Object DELETE_SHIPMENT(Map<String, Object> params) throws Exception {
        List<Map<String, Object>> findShippingList = upsFedexDao.SHIPPING_LOG_SEARCH(params);
        String trackingNum = (String) params.get("TRACKING_NUM");

        // 1. LOG에 TRACKING_NUM가 존재 하는지 확인
        if (findShippingList.stream().noneMatch(ship -> ship.get("TRACKING_NUM").equals(trackingNum))) {
            throw new IllegalArgumentException("This information has not been registered."); // 존재하지 않을 경우
        }

        // 2-1. fedex api call
        if(ShipAgentCodeType.FEDEX.equals(ShipAgentCodeType.valueOf((String) params.get("SHIP_AGENT_CD")))) {
            ShipmentCancelResponse shipmentCancelResponse = fedexService.cancelShipment(trackingNum);

            if(!shipmentCancelResponse.getOutput().isCancelledShipment()) {
                log.error("Fedex Cancel 실패");
                throw new IllegalArgumentException("Fedex Cancel 실패");
            }
        }
        // 2-2. ups api call
        else {
            upsShipService.cancelShipment(trackingNum);
        }

        // 3. 내부로직 처리
        upsFedexDao.DELETE_SHIPMENT(params);

        // 4. 성공시 return 값
        // 뭔지 모르겠으나 해당 형태로 return 중
        return new HashMap<String, Object>() {{
            put("STATUS", "SUCCESS");
            put("CODE", "SUCCESS");
            put("MESSAGE", "SUCCESS");
        }};
    }



}
