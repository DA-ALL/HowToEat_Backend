package com.daall.howtoeat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {
    REQUIRES_LOGIN(HttpStatus.BAD_REQUEST, "로그인이 필요한 서비스입니다."),
    NOT_AVAILABLE_PERMISSION(HttpStatus.BAD_REQUEST, "권한이 없습니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "유효하지 않는 JWT 입니다."),
    NOT_FOUND_AUTHENTICATION_INFO(HttpStatus.NOT_FOUND, "인증 정보를 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다. 다시 로그인 해주세요."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
