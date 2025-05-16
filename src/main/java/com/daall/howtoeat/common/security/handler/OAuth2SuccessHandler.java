package com.daall.howtoeat.common.security.handler;

import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.common.security.jwt.JwtUtil;
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
        String email = (String) attributes.get("email");

        boolean isNewUser = !userRepository.existsByEmail(email);
        System.out.println(email);

        // 동작 제대로 하는지 테스트 필요
//        UserRole role = ((UserDetailsImpl) authentication.getPrincipal()).getUser().getUserRole();
//        System.out.println(role);

        String token = jwtUtil.createAccessToken(email, UserRole.USER);

        String redirectUrl = isNewUser
                ? "http://localhost:3000/survey?email=" + email + "&token=" + token
                : "http://localhost:3000/main?token=" + token;

        response.sendRedirect(redirectUrl);
    }
}
