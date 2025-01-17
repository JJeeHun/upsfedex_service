package com.flexfit.comm.svc.ups_core.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ServiceType {
    NEXT_DAY_AIR("01", "Next Day Air"),
    SECOND_DAY_AIR("02", "2nd Day Air"),
    GROUND("03", "Ground"),
    EXPRESS("07", "Express"),
    EXPEDITED("08", "Expedited"),
    UPS_STANDARD("11", "UPS Standard"),
    THREE_DAY_SELECT("12", "3 Day Select"),
    NEXT_DAY_AIR_SAVER("13", "Next Day Air Saver"),
    NEXT_DAY_AIR_EARLY("14", "UPS Next Day Air® Early"),
    WORLDWIDE_ECONOMY_DDU("17", "UPS Worldwide Economy DDU"),
    EXPRESS_PLUS("54", "Express Plus"),
    SECOND_DAY_AIR_AM("59", "2nd Day Air A.M."),
    UPS_SAVER("65", "UPS Saver"),
    FIRST_CLASS_MAIL("M2", "First Class Mail"),
    PRIORITY_MAIL("M3", "Priority Mail"),
    EXPEDITED_MAIL_INNOVATIONS("M4", "Expedited Mail Innovations"),
    PRIORITY_MAIL_INNOVATIONS("M5", "Priority Mail Innovations"),
    ECONOMY_MAIL_INNOVATIONS("M6", "Economy Mail Innovations"),
    MAIL_INNOVATIONS_RETURNS("M7", "Mail Innovations (MI) Returns"),
    ACCESS_POINT_ECONOMY("70", "UPS Access Point™ Economy"),
    WORLDWIDE_EXPRESS_FREIGHT_MIDDAY("71", "UPS Worldwide Express Freight Midday"),
    WORLDWIDE_ECONOMY_DDP("72", "UPS Worldwide Economy DDP"),
    EXPRESS_12("74", "UPS Express®12:00"),
    HEAVY_GOODS("75", "UPS Heavy Goods"),
    TODAY_STANDARD("82", "UPS Today Standard"),
    TODAY_DEDICATED_COURIER("83", "UPS Today Dedicated Courier"),
    TODAY_INTERCITY("84", "UPS Today Intercity"),
    TODAY_EXPRESS("85", "UPS Today Express"),
    TODAY_EXPRESS_SAVER("86", "UPS Today Express Saver"),
    WORLDWIDE_EXPRESS_FREIGHT("96", "UPS Worldwide Express Freight");

    @JsonValue
    private final String code;
    private final String description;

    public static ServiceType fromCode(String code) {
        return Arrays.stream(values())
            .filter(serviceType -> serviceType.getCode().equals(code))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown ServiceType code: " + code));
    }
}
