package com.flexfit.comm.svc.ups_core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * <b>Color Information : </b><b style="color: #ee6196;">required</b> ,   <b style="color: #86da98;">option</b>
 *
 * <ul style="font-size:10px;">
 *   <li><b style="color: #ee6196;">name</b> : Company Name 1~35<br/>
 *   <li><b style="color: #86da98;">attentionName</b> : <br/>
 *   <li><b style="color: #86da98;">companyDisplayableName</b> : <br/>
 *   <li><b style="color: #86da98;">taxIdentificationNumber</b> : <br/>
 *   <li><b style="color: #86da98;">phone</b> : {@link Phone}<br/>
 *   <li><b style="color: #86da98;">faxNumber</b> : <br/>
 *   <li><b style="color: #86da98;">eMailAddress</b> : <br/>
 *   <li><b style="color: #ee6196;">address</b> : {@link Address}<br/>
 * </ul>
 */
@SuperBuilder
@Getter @ToString
public class Company {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("AttentionName")
    private String attentionName;

    @JsonProperty("CompanyDisplayableName")
    private String companyDisplayableName;

    @Deprecated
    @JsonProperty("TaxIdentificationNumber")
    private String taxIdentificationNumber;

    @JsonProperty("Phone")
    private Phone phone;

    @JsonProperty("FaxNumber")
    private String faxNumber;

    @JsonProperty("EMailAddress")
    private String eMailAddress;

    @JsonProperty("Address")
    private Address address;
}
