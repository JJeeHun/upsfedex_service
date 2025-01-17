package com.flexfit.comm.svc.ups_core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder @Jacksonized
@Getter @ToString
public class Address {

    @JsonProperty("AddressLine")
    private List<String> addressLine;

    @JsonProperty("City")
    private String city;

    @JsonProperty("StateProvinceCode")
    private String stateProvinceCode;

    @JsonProperty("PostalCode")
    private String postalCode;

    @JsonProperty("CountryCode")
    private String countryCode;
}
