package com.daall.howtoeat.common.security.jwt;

import com.daall.howtoeat.common.enums.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j(topic = "JwtUtil")
public class JwtUtil {
    // accessToken 토큰 헤더
    public static final String AUTH_ACCESS_HEADER = "Authorization";
    // refreshToken 토큰 헤더
    public static final String AUTH_REFRESH_HEADER = "RefreshToken";
    // 사용자 권한 키
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // accessToken 만료 시간 (60분)
    private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000L;
    // refreshToken 만료 시간 (2주)
    private final long REFRESH_TOKEN_EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct // 의존성 주입 완료 후 초기화 작업 수행
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // accessToken 생성
    public String createAccessToken(String userId, UserRole role){
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(userId)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    //OAuth2 첫 회원가입 시, 유저 정보를 담은 accessToken 생성
    public String createAccessTokenWithClaims(Map<String, Object> claims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 1000L * 60 * 60); // 1시간

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // refreshToken 생성
    public String createRefreshToken(String userId, UserRole role){
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(userId)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String getAccessTokenFromHeader(HttpServletRequest request){
        String accessToken = request.getHeader(AUTH_ACCESS_HEADER);
        if(StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
            return accessToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

//    public String getRefreshTokenFromHeader(HttpServletRequest request){
//        String accessToken = request.getHeader(AUTH_REFRESH_HEADER);
//        if(StringUtils.hasText(accessToken) && accessToken.startsWith(BEARER_PREFIX)) {
//            return accessToken.substring(BEARER_PREFIX.length());
//        }
//        return null;
//    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("RefreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
//            throw new CustomException(ErrorType.INVALID_JWT);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
//            throw new CustomException(ErrorType.INVALID_JWT);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
//            throw new CustomException(ErrorType.INVALID_JWT);
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 헤더에 AccessToken 담기
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken){
        response.setHeader(AUTH_ACCESS_HEADER, accessToken);
    }

    // 리프레시 토큰을 HttpOnly 쿠키에 담아 응답에 추가
//    public void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
//        // "Bearer " 접두어 제거
//        String tokenValue = refreshToken.substring(BEARER_PREFIX.length());
//
//        jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("RefreshToken", tokenValue);
//        cookie.setHttpOnly(true); // 자바스크립트 접근 차단
//        cookie.setSecure(false);   // HTTPS에서만 전송 (local 테스트시에 false로)
//        cookie.setPath("/");      // 모든 경로에서 쿠키 전송
//        cookie.setMaxAge((int) (REFRESH_TOKEN_EXPIRE_TIME / 1000)); // 단위: 초
//
//        response.addCookie(cookie);
//
//        // TODO: SameSite 알아보기
//        // SameSite 설정 추가 필요 (자바 서블릿은 기본적으로 직접 지원하지 않음)
////        response.setHeader("Set-Cookie", String.format(
////                "RefreshToken=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=Strict",
////                tokenValue, (int) (REFRESH_TOKEN_EXPIRE_TIME / 1000)
////        ));
//    }
    public void addRefreshTokenToCookie(HttpServletResponse response, String refreshToken) {
        String tokenValue = refreshToken.substring(BEARER_PREFIX.length());

        ResponseCookie cookie = ResponseCookie.from("RefreshToken", tokenValue)
                .httpOnly(true)
                .secure(false) // HTTPS 환경에서만 사용
//                .sameSite("None") // 💡 이게 핵심!
                .path("/")
                .maxAge(REFRESH_TOKEN_EXPIRE_TIME / 1000)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
