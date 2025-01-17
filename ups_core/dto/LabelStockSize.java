package com.flexfit.comm.svc.ups_core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder @Jacksonized
@Getter @ToString
public class LabelStockSize {

    @JsonProperty("Height")
    private String height;

    @JsonProperty("Width")
    private String width;
}
