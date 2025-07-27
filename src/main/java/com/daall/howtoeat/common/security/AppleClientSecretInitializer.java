package com.daall.howtoeat.common.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleClientSecretInitializer {

    @Value("${APPLE_TEAM_ID}")
    private String teamId;

    @Value("${APPLE_CLIENT_ID}")
    private String clientId;

    @Value("${APPLE_KEY_ID}")
    private String keyId;

    @Value("${APPLE_PRIVATE_KEY}")
    private String privateKey;

    @PostConstruct
    public void setAppleClientSecret() {

        System.out.println("클라이언트 시크릿 만들기 시작");
        String clientSecret = AppleClientSecretProvider.createClientSecret(
                teamId, clientId, keyId, privateKey
        );

        System.setProperty("APPLE_CLIENT_SECRET", clientSecret);

        System.out.println("[AppleClientSecretInitializer] 생성된 client_secret:");
        System.out.println(clientSecret);
    }
}
