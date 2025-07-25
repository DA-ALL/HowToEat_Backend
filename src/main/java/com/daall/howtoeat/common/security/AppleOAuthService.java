package com.daall.howtoeat.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AppleOAuthService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${APPLE_CLIENT_ID}")
    private String clientId;

    @Value("${APPLE_TEAM_ID}")
    private String teamId;

    @Value("${APPLE_KEY_ID}")
    private String keyId;

    @Value("${APPLE_REDIRECT_URI}")
    private String redirectUri;

    @Value("${APPLE_PRIVATE_KEY}")
    private String privateKey; // ← 여기만 추가

    public Map<String, Object> getToken(String authorizationCode) {

        String clientSecret = AppleClientSecretProvider.createClientSecret(
                teamId, clientId, keyId, privateKey
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", authorizationCode);
        body.add("redirect_uri", redirectUri);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://appleid.apple.com/auth/token",
                request,
                Map.class
        );

        return response.getBody();
    }
}
