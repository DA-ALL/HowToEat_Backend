package com.daall.howtoeat.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {
        System.out.println("loadUser 시작");

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> userInfo = new HashMap<>();

        System.out.println("[CustomOAuth2UserService] OAUTH 프로바이더 : " + registrationId);

        if ("apple".equals(registrationId)) {
            String idToken = (String) userRequest.getAdditionalParameters().get("id_token");

            if (idToken == null) {
                throw new OAuth2AuthenticationException("Apple id_token 누락");
            }

            DecodedJWT decoded = JWT.decode(idToken);

            userInfo.put("provider", registrationId);
            userInfo.put("email", decoded.getClaim("email").asString());
            userInfo.put("name", decoded.getClaim("name").asString());

            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                    userInfo,
                    "email"
            );
        }

        // Apple이 아닌 경우만 기본 처리
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        if ("naver".equals(registrationId)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            userInfo.put("provider", registrationId);
            userInfo.put("name", response.get("name"));
            userInfo.put("email", response.get("email"));
            userInfo.put("birthyear", response.get("birthyear"));
            userInfo.put("birthday", response.get("birthday"));
            userInfo.put("gender", response.get("gender"));
            userInfo.put("profile_image", response.get("profile_image"));

        } else if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            userInfo.put("provider", registrationId);
            userInfo.put("name", profile.get("nickname"));
            userInfo.put("email", kakaoAccount.get("email"));
            userInfo.put("profile_image", profile.get("profile_image_url"));
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                userInfo,
                "email"
        );
    }
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest)
//            throws OAuth2AuthenticationException {
//        System.out.println("loadUser 시작");
//
//
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//        String registrationId = userRequest.getClientRegistration().getRegistrationId();
//
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        Map<String, Object> userInfo = new HashMap<>();
//
//        System.out.println("[CustomOAuth2UserService] OAUTH 프로바이더 : " + registrationId);
//
//        if ("naver".equals(registrationId)) {
//            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//
//            userInfo.put("provider", registrationId);
//            userInfo.put("name", response.get("name"));
//            userInfo.put("email", response.get("email"));
//            userInfo.put("birthyear", response.get("birthyear"));
//            userInfo.put("birthday", response.get("birthday"));
//            userInfo.put("gender", response.get("gender"));
//            userInfo.put("profile_image", response.get("profile_image"));
//
//        } else if ("kakao".equals(registrationId)) {
//            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
//            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
//
//            userInfo.put("provider", registrationId);
//            userInfo.put("name", profile.get("nickname"));
//            userInfo.put("email", kakaoAccount.get("email"));
//            userInfo.put("profile_image", profile.get("profile_image_url"));
//        }
//        else if ("apple".equals(registrationId)) {
//            String idToken = (String) userRequest.getAdditionalParameters().get("id_token");
//
//            if (idToken == null) {
//                throw new OAuth2AuthenticationException("Apple id_token 누락");
//            }
//
//            DecodedJWT decoded = JWT.decode(idToken);
//
//            userInfo.put("provider", registrationId);
//            userInfo.put("email", decoded.getClaim("email").asString());
//            userInfo.put("name", decoded.getClaim("name").asString());
//        }
//
//        // 디버깅 출력
//        userInfo.forEach((k, v) -> System.out.println(k + ": " + v));
//
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//                userInfo,
//                "email"  // 고유 식별자
//        );
//    }
}
