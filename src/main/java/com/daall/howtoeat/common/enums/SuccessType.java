package com.daall.howtoeat.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessType {
    USER_SIGN_UP_SUCCESS(HttpStatus.OK, "회원가입에 성공하였습니다."),
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    ADMIN_ACCOUNT_CREATE_SUCCESS(HttpStatus.OK, "관리자 계정 생성에 성공하였습니다."),
    GET_DAILY_KCAL_SUMMARIES_SUSSESS(HttpStatus.OK, "날짜별 목표칼로리, 섭취칼로리 조회에 성공하였습니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
