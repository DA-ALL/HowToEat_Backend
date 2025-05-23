package com.daall.howtoeat.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessType {
    USER_SIGN_UP_SUCCESS(HttpStatus.OK, "회원가입에 성공하였습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
