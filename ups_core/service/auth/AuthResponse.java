package com.flexfit.comm.svc.ups_core.service.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder @Jacksonized
@Getter @ToString
public class AuthResponse {

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("issued_at")
    private String issuedAt;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("refresh_count")
    private String refreshCount;

    @JsonProperty("status")
    private String status;

    public boolean isExpired() {
        return false;
    }
}
