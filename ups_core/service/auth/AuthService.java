package com.flexfit.comm.svc.ups_core.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@Slf4j
public class AuthService {
    private final String URL;
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final String ACCOUNT_NUMBER;
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthResponse authResponse;
    private final boolean isProd;

    public AuthService(String clientId, String clientSecret, String accountNumber) {
        this(clientId, clientSecret, accountNumber, false);
    }

    public AuthService(String clientId, String clientSecret, String accountNumber, boolean isProd) {
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
        this.ACCOUNT_NUMBER = accountNumber;
        this.isProd = isProd;
        this.URL = getDomain() + "/security/v1/oauth/token";
    }

    public String getToken() {
        if (isExpired()) {
            log.info("UPS oAuthService 실행 [ Mode = {} ]", this.isProd ? "PROD" : "TEST");
            log.info("UPS oAuthService 실행 [ url = {} ]",this.URL);
            // 요청 본문 설정
            HttpEntity<String> requestEntity = new HttpEntity<>("grant_type=client_credentials", getHeaders());
            // Token 발급 요청
            authResponse = execute(requestEntity);
            log.info("UPS oAuthService 성공 [ access token = {} ]", authResponse.getAccessToken());
        }

        assert authResponse != null;
        return authResponse.getAccessToken();
    }

    public String getBearerToken() {
        return "Bearer " + getToken();
    }

    public String getDomain() {
        final String PROD_URL = "https://onlinetools.ups.com";
        final String TEST_URL = "https://wwwcie.ups.com";

        return this.isProd ? PROD_URL : TEST_URL;
    }

    private boolean isExpired() {
        return authResponse == null || authResponse.isExpired();
    }

    private AuthResponse execute(HttpEntity<String> request) {
        try {
            ResponseEntity<AuthResponse> result = restTemplate.postForEntity(URL, request, AuthResponse.class);
            return result.getBody();
        } catch (HttpClientErrorException e) {
            log.error("UPS oAuthService 인증 에러");
            HttpHeaders responseHeaders = e.getResponseHeaders();
            if( responseHeaders != null ) {
                List<String> errorCode = responseHeaders.get("Errorcode");
                List<String> errorDescription = responseHeaders.get("Errordescription");
                log.error("UPS oAuthService Error Code = " + errorCode);
                log.error("UPS oAuthService Error Description = " + errorDescription);
            }
            throw e;
        }
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("x-merchant-id", ACCOUNT_NUMBER);
        headers.add("Authorization", getAuthorization());
        return headers;
    }

    private String getAuthorization() {
        byte[] authByte = String.format("%s:%s", CLIENT_ID, CLIENT_SECRET).getBytes();
        return "Basic " + Base64.getEncoder().encodeToString(authByte);
    }
}
