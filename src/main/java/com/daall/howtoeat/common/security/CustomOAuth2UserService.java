package com.daall.howtoeat.common.security;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collections;
import java.util.Map;

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 네이버인 경우 response 안에 유저 정보가 있음
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        // 필요한 정보 꺼내기
        String name = (String) response.get("name");
        String email = (String) response.get("email");
        String birthyear = (String) response.get("birthyear");
        String birthday = (String) response.get("birthday");
        String gender = (String) response.get("gender");
        String profile_image = (String) response.get("profile_image");

        System.out.println(name);
        System.out.println(email);
        System.out.println(birthyear);
        System.out.println(birthday);
        System.out.println(profile_image);
        System.out.println(gender);

        // 필요하면 여기에 DB 저장 또는 JWT 발급 등 추가 작업

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                response, // attributes
                "email"   // 유저를 식별할 key 값 (보통 email, id 등)
        );
    }
}
