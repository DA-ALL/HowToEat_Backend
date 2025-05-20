package com.daall.howtoeat.common.security.handler;

import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.common.security.jwt.JwtUtil;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        System.out.println("OAuth2 attributes: " + attributes); // 디버그

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String birthyear = (String) attributes.get("birthyear");
        String birthday = (String) attributes.get("birthday");
        String gender = (String) attributes.get("gender");

        boolean isNewUser = !userRepository.existsByEmail(email);

        Map<String, Object> claims = Map.of(
                "email", email,
                "name", name,
                "birthyear", birthyear,
                "birthday", birthday,
                "gender", gender,
                "isNew", isNewUser
        );

        String redirectUrl = "";
        if(isNewUser) {
            String claimsToken = jwtUtil.createAccessTokenWithClaims(claims);
            redirectUrl = "http://localhost:3000/survey?token=" + claimsToken;
        } else {
            String refreshToken = jwtUtil.createRefreshToken(email, UserRole.USER);

            User user = userRepository.findByEmail(email).get();
            user.saveRefreshToken(refreshToken);
            userRepository.save(user);
            jwtUtil.addRefreshTokenToCookie(response, refreshToken);

            redirectUrl = "http://localhost:3000/main";
        }

        response.sendRedirect(redirectUrl);
    }
}
