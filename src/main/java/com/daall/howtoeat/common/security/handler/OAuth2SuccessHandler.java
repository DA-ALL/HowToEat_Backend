package com.daall.howtoeat.common.security.handler;

import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.common.security.jwt.JwtUtil;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${DOMAIN_URL}")
    private String domainUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 제공자 판별
        String provider = attributes.get("provider") != null ? attributes.get("provider").toString() : null;

        System.out.println("[onAuthenticationSuccess] OAUTH 프로바이더 : " + provider);
        System.out.println("[onAuthenticationSuccess] OAUTH 로그인 유저 정보: " + oAuth2User);

        // 공통 사용자 정보
        String email = null;
        String name = null;
        String birthyear = null;
        String birthday = null;
        String gender = null;
        String profileImage = null;

        // provider별 정보 매핑
        if ("kakao".equals(provider)) {
            email = attributes.get("email") != null ? attributes.get("email").toString() : null;
            name = attributes.get("name") != null ? attributes.get("name").toString() : null;
            profileImage = attributes.get("profile_image") != null ? attributes.get("profile_image").toString() : null;
        } else {
            email = attributes.get("email") != null ? attributes.get("email").toString() : null;
            name = attributes.get("name") != null ? attributes.get("name").toString() : null;
            birthyear = attributes.get("birthyear") != null ? attributes.get("birthyear").toString() : null;
            birthday = attributes.get("birthday") != null ? attributes.get("birthday").toString() : null;
            gender = attributes.get("gender") != null ? attributes.get("gender").toString() : null;
            profileImage = attributes.get("profile_image") != null ? attributes.get("profile_image").toString() : null;

        }

        if (email == null || email.equals("null")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "OAuth2 로그인 실패: 이메일 누락");
            return;
        }

        boolean isNewUser = !userRepository.existsByEmail(email);
        System.out.println("isNewUser: " + isNewUser);

        // JWT claims 구성
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("name", name);
        claims.put("profile_image_url", profileImage);
        claims.put("isNew", isNewUser);

        if ("naver".equals(provider)) {
            claims.put("birthyear", birthyear);
            claims.put("birthday", birthday);
            claims.put("gender", gender);
        }

        // 리다이렉트 URL 설정
        String redirectUrl;


        if (isNewUser) {
            String claimsToken = jwtUtil.createAccessTokenWithClaims(claims);
            redirectUrl = "http://" + domainUrl + ":3000/survey?token=" + claimsToken;
        } else {
            String refreshToken = jwtUtil.createRefreshToken(email, UserRole.USER);
             User user = userRepository.findByEmail(email).orElseThrow(); // orElseThrow로 안정성
            user.saveRefreshToken(refreshToken);
            userRepository.save(user);
            jwtUtil.addRefreshTokenToCookie(response, refreshToken);
            redirectUrl = "http://" + domainUrl + ":3000/main";
        }

        response.sendRedirect(redirectUrl);
    }
}
