package com.daall.howtoeat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    REQUIRES_LOGIN(HttpStatus.BAD_REQUEST, "로그인이 필요한 서비스입니다."),
    NOT_AVAILABLE_PERMISSION(HttpStatus.BAD_REQUEST, "권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
