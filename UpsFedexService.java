package com.flexfit.comm.svc;

import java.util.Map;

import com.safecnc.request.NexacroRequest;

/*******************************************************************
<h1>NAME : </h1>
<p>UPS_FEDEX</p>
<h2>DESC : </h2>
<p>설명</p>
<h3>REV.:</h3>
<pre>
Date        Worker           Description
----------  ---------------  ------------------------------------
2022-09-19  임하준             최초생성
</pre>
********************************************************************/

public interface UpsFedexService {
	
	Object UPS_FEDEX_SHIPPING(Map<String, Object> mapVariableList)throws Exception;

	Object DELETE_SHIPMENT(Map<String, Object> mapVariableList) throws Exception;
}
