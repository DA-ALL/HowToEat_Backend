package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.security.jwt.JwtUtil;
import com.daall.howtoeat.domain.user.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public void issueTokens(User user, HttpServletResponse response){
        System.out.println("회원가입시 리프레쉬 토큰 발행하기");
        String accessToken = jwtUtil.createAccessToken(user.getEmail(), user.getUserRole());
        String refreshToken = jwtUtil.createRefreshToken(user.getEmail(), user.getUserRole());

        userService.updateRefreshToken(user, refreshToken);

        jwtUtil.setHeaderAccessToken(response, accessToken);
        jwtUtil.addRefreshTokenToCookie(response, refreshToken);
    }

}
