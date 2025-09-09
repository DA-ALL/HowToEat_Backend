package com.daall.howtoeat.common.security.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.security.jwt.JwtUtil;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Value("${REDIRECT_URL}")
    private String url;

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
        } else if("naver".equals(provider)) {
            email = attributes.get("email") != null ? attributes.get("email").toString() : null;
            name = attributes.get("name") != null ? attributes.get("name").toString() : null;
            birthyear = attributes.get("birthyear") != null ? attributes.get("birthyear").toString() : null;
            birthday = attributes.get("birthday") != null ? attributes.get("birthday").toString() : null;
            gender = attributes.get("gender") != null ? attributes.get("gender").toString() : null;
            profileImage = attributes.get("profile_image") != null ? attributes.get("profile_image").toString() : null;
        } else if ("apple".equals(provider)) {
            String idToken = ((OidcUser) oAuth2User).getIdToken().getTokenValue();
            DecodedJWT jwt = JWT.decode(idToken);
            String givenName = jwt.getClaim("given_name").asString();
            String familyName = jwt.getClaim("family_name").asString();
            System.out.println("apple given name: " + givenName);
            System.out.println("apple family name: " + familyName);

            email = attributes.get("email") != null ? attributes.get("email").toString() : null;
            name = attributes.get("name") != null ? attributes.get("name").toString() : null;
            String givenName2 = attributes.get("given_name") != null ? attributes.get("given_name").toString() : null;
            System.out.println("givenName2 = " + givenName2);
            System.out.println("apple name = " + name);
            System.out.println("apple email = " + email);
            profileImage = null; // Apple은 프로필 이미지 제공 안 함
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
        claims.put("signup_provider", provider != null ? provider.toUpperCase() : "");

        if ("naver".equals(provider)) {
            claims.put("birthyear", birthyear);
            claims.put("birthday", birthday);
            claims.put("gender", gender);
        }

        // 리다이렉트 URL 설정
        String redirectUrl;


        if (isNewUser) {
            String claimsToken = jwtUtil.createAccessTokenWithClaims(claims);
            redirectUrl = url + "/signup/terms-privacy?token=" + claimsToken;

        } else {
            User user = userRepository.findByEmail(email).orElseThrow(); // orElseThrow로 안정성

            // 유저의 이메일이 같지만, signupProvider가 다를경우(naver로 회원가입했는데 같은 이메일로 카카오 로그인 시도할 경우)
            if(!user.getSignup_provider().toString().equals(provider != null ? provider.toUpperCase() : "")){
//                String message = ErrorType.ALREADY_EXISTS_EMAIL.getMessage();
//                String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());

                String encodedUserProvider = URLEncoder.encode(user.getSignup_provider().toString(), StandardCharsets.UTF_8);

                response.setStatus(HttpServletResponse.SC_FOUND); // 302 redirect
                response.setHeader("Location", url + "/email-exists?provider=" + encodedUserProvider);
                response.flushBuffer();
                return;
            }

            String refreshToken = jwtUtil.createRefreshToken(email, user.getUserRole());
            user.saveRefreshToken(refreshToken);
            userRepository.save(user);
            jwtUtil.addRefreshTokenToCookie(response, refreshToken);
            redirectUrl = url + "/main";
        }

        response.sendRedirect(redirectUrl);
    }
}
