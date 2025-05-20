package com.daall.howtoeat.common.security.jwt;

import com.daall.howtoeat.common.enums.UserRole;
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
        System.out.println("검증 시작");
        String accessToken = jwtUtil.getAccessTokenFromHeader(request);

        if(StringUtils.hasText(accessToken)){
            if(jwtUtil.validateToken(accessToken)){
                // accessToken이 유효할 때
                authenticateWithAccessToken(accessToken);
            } else {
                // accessToken이 유효하지 않을 때
                validateAndAuthenticateWithRefreshToken(request, response);
            }
        } else {
            // accessToken이 없을때 리프래시 토큰 사용.
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
//            throw new CustomException(ErrorType.NOT_FOUND_AUTHENTICATION_INFO);
        }
    }

    // accessToken이 유효하지 않은 경우, 리프레스 토큰 검증 및 엑세스토큰 재발급
    public void validateAndAuthenticateWithRefreshToken(HttpServletRequest request, HttpServletResponse response){

        String refreshToken = jwtUtil.getRefreshTokenFromCookie(request);
        System.out.println("리프래쉬 토큰 확인: " + refreshToken);

        if(StringUtils.hasText(refreshToken) && jwtUtil.validateToken(refreshToken)){
            Claims info = jwtUtil.getUserInfoFromToken(refreshToken);
            UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(info.getSubject());
            User user = userDetails.getUser();

            if(user.getRefreshToken().equals(refreshToken) && jwtUtil.validateToken(refreshToken)){
                System.out.println("리프레쉬토큰 검증");
                UserRole role = user.getUserRole();
                String newAccessToken = jwtUtil.createAccessToken(info.getSubject(), role);
                jwtUtil.setHeaderAccessToken(response, newAccessToken);

                try{
                    setAuthentication(info.getSubject());
                    System.out.println("헤더에 엑세스토큰 보내기");
                } catch (Exception e) {
                    log.error(e.getMessage());
                    System.out.println("에러");
//                    throw new CustomException(ErrorType.NOT_FOUND_AUTHENTICATION_INFO);
                }
            } else {
//                throw new CustomException(ErrorType.INVALID_REFRESH_TOKEN);
            }
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
