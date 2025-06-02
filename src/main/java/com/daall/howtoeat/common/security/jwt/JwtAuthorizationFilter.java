package com.daall.howtoeat.common.security.jwt;

import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.common.security.UserDetailsServiceImpl;
import com.daall.howtoeat.domain.user.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "Jwt 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // 로그인 요청은 토큰 검증하지 않음
        if ("/admin/login".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtUtil.getAccessTokenFromHeader(request);

        if (StringUtils.hasText(accessToken) && jwtUtil.validateToken(accessToken)) {
            authenticateWithAccessToken(accessToken);
        } else {
            // accessToken이 없거나 유효하지 않은 경우
            validateAndAuthenticateWithRefreshToken(request, response);
        }

        filterChain.doFilter(request, response);
    }

    // accessToken이 유효한 경우
    public void authenticateWithAccessToken(String token){
        Claims info = jwtUtil.getUserInfoFromToken(token);

        try {
            setAuthentication(info.getSubject());
        } catch (Exception e){
            log.error("username = {}, message = {}", info.getSubject(), "인증 정보를 찾을 수 없습니다.");
            throw new CustomException(ErrorType.NOT_FOUND_AUTHENTICATION_INFO);
        }
    }

    // accessToken이 유효하지 않은 경우, 리프레쉬 토큰 검증 및 엑세스토큰 재발급
    public void validateAndAuthenticateWithRefreshToken(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = jwtUtil.getRefreshTokenFromCookie(request);

        if (!StringUtils.hasText(refreshToken)) {
            throw new CustomException(ErrorType.MISSING_REFRESH_TOKEN);
        }

        if (!jwtUtil.validateToken(refreshToken)) {
            throw new CustomException(ErrorType.INVALID_REFRESH_TOKEN);
        }

        Claims claims = jwtUtil.getUserInfoFromToken(refreshToken);
        String username = claims.getSubject();
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);
        User user = userDetails.getUser();

        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new CustomException(ErrorType.INVALID_REFRESH_TOKEN);
        }

        String newAccessToken = jwtUtil.createAccessToken(username, user.getUserRole());
        jwtUtil.setHeaderAccessToken(response, newAccessToken);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        try {
            setAuthentication(username);
        } catch (Exception e) {
            log.error("Failed to set authentication: {}", e.getMessage(), e);
            throw new CustomException(ErrorType.NOT_FOUND_AUTHENTICATION_INFO);
        }
    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
