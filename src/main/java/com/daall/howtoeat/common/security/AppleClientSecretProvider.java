package com.daall.howtoeat.common.security;
import com.daall.howtoeat.common.security.AppleKeyUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.time.Instant;
import java.util.Date;

public class AppleClientSecretProvider {

    public static String createClientSecret(String teamId, String clientId, String keyId, String privateKey) {
        Instant now = Instant.now();
        ECPrivateKey privateKeyObj = AppleKeyUtil.getPrivateKeyFromString(privateKey);

        return JWT.create()
                .withIssuer(teamId)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plusSeconds(15777000))) // 6개월
                .withAudience("https://appleid.apple.com")
                .withSubject(clientId)
                .withKeyId(keyId)
                .sign(Algorithm.ECDSA256(null, privateKeyObj));
    }
}
