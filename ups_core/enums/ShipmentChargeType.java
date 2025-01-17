package com.flexfit.comm.svc.ups_core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ShipmentChargeType {
    TRANSPORTATION("01"), DUTIES_AND_TAXES("02"), BROKER("03");

    @JsonValue
    private String code;

    ShipmentChargeType(String code) {
        this.code = code;
    }
}
