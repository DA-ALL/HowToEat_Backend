package com.daall.howtoeat.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessType {

    // ======================= USER =======================
    CREATE_USER_SUCCESS(HttpStatus.CREATED, "회원가입에 성공하였습니다."),
    CREATE_ADMIN_ACCOUNT_SUCCESS(HttpStatus.CREATED, "관리자 계정 생성에 성공하였습니다."),
    GET_ADMIN_ACCOUNT_SUCCESS(HttpStatus.OK, "관리자 계정 조회에 성공하였습니다."),
    GET_ALL_ADMIN_ACCOUNTS_SUCCESS(HttpStatus.OK, "관리자 계정 전체 조회에 성공하였습니다."),
    GET_USER_SIGNUP_DATE_SUCCESS(HttpStatus.OK, "유저 회원가입 날짜 조회에 성공하였습니다."),
    UPDATE_ADMIN_ACCOUNT_SUCCESS(HttpStatus.OK, "관리자 계정 변경에 성공하였습니다."),
    DELETE_ADMIN_ACCOUNT_SUCCESS(HttpStatus.OK, "관리자 계정 삭제에 성공하였습니다."),
    ADMIN_LOGIN_SUCCESS(HttpStatus.OK, "관리자 로그인에 성공하였습니다."),
    GET_ALL_USERS_SUCCESS(HttpStatus.OK, "전체 유저 조회에 성공하였습니다."),
    UPDATE_USER_ROLE_SUCCESS(HttpStatus.OK, "유저 권한 변경에 성공하였습니다."),
    UPDATE_PT_MEMBER_SUCCESS(HttpStatus.OK, "넥스트짐 회원 변경에 성공하였습니다."),

    // ======================= USER DETAILS =======================
    GET_USER_BASIC_INFO_SUCCESS(HttpStatus.OK, "기본 유저정보 조회에 성공하였습니다."),
    GET_USER_DETAIL_INFO_SUCCESS(HttpStatus.OK, "디테일 유저정보 조회에 성공하였습니다."),
    UPDATE_USER_DETAIL_INFO_SUCCESS(HttpStatus.OK, "디테일 유저 정보가 수정되었습니다."),
    UPDATE_USER_WEIGHT_SUCCESS(HttpStatus.OK, "유저 몸무게가 수정되었습니다."),
    UPDATE_USER_HEIGHT_SUCCESS(HttpStatus.OK, "유저 키가 수정되었습니다."),
    GET_USER_WEIGHT_SUCCESS(HttpStatus.OK, "몸무게 조회에 성공하였습니다."),

    // ======================= CONSUMED_FOOD =======================
    CREATE_CONSUMED_FOOD_SUCCESS(HttpStatus.CREATED, "섭취음식 추가에 성공하였습니다."),
    DELETE_CONSUMED_FOOD_SUCCESS(HttpStatus.OK, "섭취 음식 삭제에 성공하였습니다."),
    GET_CONSUMED_FOOD_BY_MEAL_SUCCESS(HttpStatus.OK, "끼니별 섭취 음식 조회에 성공하였습니다."),
    GET_CONSUMED_FOOD_BY_DATE_SUCCESS(HttpStatus.OK, "날짜별 섭취 음식 리스트 조회에 성공하였습니다."),

    // ======================= DAILY_SUMMARIES =======================
    GET_DAILY_KCAL_SUMMARIES_SUCCESS(HttpStatus.OK, "날짜별 목표칼로리, 섭취칼로리 조회에 성공하였습니다."),
    GET_DAILY_MACROS_SUMMARIES_SUCCESS(HttpStatus.OK, "날짜별 탄단지 조회에 성공하였습니다."),
    GET_USER_TARGET_KCAL_SUCCESS(HttpStatus.OK, "목표 칼로리 조회에 성공하였습니다."),
    GET_USER_KCAL_BY_DATE_SUCCESS(HttpStatus.OK, "날짜별 섭취 칼로리 조회에 성공하였습니다."),
    GET_USER_MACROS_BY_MEALTIME_SUCCESS(HttpStatus.OK, "끼니별 탄단지 조회에 성공하였습니다."),

    // ======================= FAVORITE =======================
    CREATE_FAVORITE_SUCCESS(HttpStatus.CREATED, "즐겨찾기 추가에 성공하였습니다."),
    GET_FAVORITE_FOOD_SUCCESS(HttpStatus.OK, "즐겨찾기 추가에 성공하였습니다."),
    DELETE_FAVORITE_SUCCESS(HttpStatus.OK, "즐겨찾기 삭제에 성공하였습니다."),
    CREATE_FAVORITE_FOOD_SUCCESS(HttpStatus.CREATED, "즐겨찾기 음식 추가에 성공하였습니다."),

    // ======================= FOOD =======================
    GET_FOOD_SUCCESS(HttpStatus.OK, "음식 조회에 성공하였습니다."),
    CREATE_FOOD_SUCCESS(HttpStatus.CREATED, "음식 추가에 성공하였습니다."),
    UPDATE_FOOD_SUCCESS(HttpStatus.OK, "음식 수정에 성공하였습니다."),
    DELETE_FOOD_SUCCESS(HttpStatus.OK, "음식 삭제에 성공하였습니다."),
    CREATE_FOOD_SHARE_SUCCESS(HttpStatus.CREATED, "음식 공유에 성공하였습니다."),
    GET_ALL_FOODS_SUCCESS(HttpStatus.OK, "전체 음식 조회에 성공하였습니다."),
    GET_FOOD_DETAIL_SUCCESS(HttpStatus.OK, "음식 세부 정보 조회에 성공하였습니다."),
    GET_ALL_USER_FOODS_SUCCESS(HttpStatus.OK, "유저 음식 전체 조회에 성공하였습니다."),
    GET_USER_FOOD_DETAIL_SUCCESS(HttpStatus.OK, "유저 음식 상세 조회에 성공하였습니다."),
    GET_ALL_RECOMMEND_FOODS_SUCCESS(HttpStatus.OK, "추천 음식 전체 조회에 성공하였습니다."),
    GET_RECOMMEND_FOOD_DETAIL_SUCCESS(HttpStatus.OK, "추천 음식 단일 조회에 성공하였습니다."),

    // ======================= CONSUMED_FOOD =======================
    ADD_CONSUMED_FOOD_SUCCESS(HttpStatus.CREATED, "음식 등록에 성공하였습니다."),

    // ======================= STATS =======================
    GET_USER_STATISTICS_SUCCESS(HttpStatus.OK, "통계 조회에 성공하였습니다."),
    GET_USER_DIET_STATS_SUCCESS(HttpStatus.OK, "식단 등록 횟수 통계 조회에 성공하였습니다."),

    // ======================= TRAINER =======================
    CREATE_TRAINER_SUCCESS(HttpStatus.CREATED, "트레이너 추가에 성공하였습니다."),
    UPDATE_TRAINER_SUCCESS(HttpStatus.OK, "트레이너 수정에 성공하였습니다."),
    DELETE_TRAINER_SUCCESS(HttpStatus.OK, "트레이너 삭제에 성공하였습니다."),
    GET_ALL_TRAINERS_SUCCESS(HttpStatus.OK, "트레이너 전체 조회에 성공하였습니다."),
    GET_TRAINER_DETAIL_SUCCESS(HttpStatus.OK, "트레이너 단일 조회에 성공하였습니다."),
    GET_TRAINER_MEMBERS_SUCCESS(HttpStatus.OK, "트레이너의 회원 전체 조회에 성공하였습니다."),
    CREATE_TRAINER_MEMBER_SUCCESS(HttpStatus.CREATED, "트레이너의 멤버 추가에 성공하였습니다."),
    DELETE_TRAINER_MEMBER_SUCCESS(HttpStatus.OK, "트레이너의 멤버 삭제에 성공하였습니다."),

    // ======================= GYM =======================
    CREATE_GYM_SUCCESS(HttpStatus.CREATED, "헬스장 추가에 성공하였습니다."),
    UPDATE_GYM_SUCCESS(HttpStatus.OK, "헬스장 수정에 성공하였습니다."),
    DELETE_GYM_SUCCESS(HttpStatus.OK, "헬스장 삭제에 성공하였습니다."),
    GET_ALL_GYMS_SUCCESS(HttpStatus.OK, "헬스장 전체 조회에 성공하였습니다."),
    GET_GYM_DETAIL_SUCCESS(HttpStatus.OK, "헬스장 단일 조회에 성공하였습니다."),

    // ======================= NOTICE =======================
    CREATE_NOTICE_SUCCESS(HttpStatus.CREATED, "공지사항 추가에 성공하였습니다."),
    GET_ALL_NOTICES_SUCCESS(HttpStatus.OK, "공지사항 전체 조회에 성공하였습니다."),
    GET_NOTICE_DETAIL_SUCCESS(HttpStatus.OK, "공지사항 단일 조회에 성공하였습니다."),
    UPDATE_NOTICE_SUCCESS(HttpStatus.OK, "공지사항 수정에 성공하였습니다."),
    DELETE_NOTICE_SUCCESS(HttpStatus.OK, "공지사항 삭제에 성공하였습니다."),

    CREATE_PTMEMBER_SUCCESS(HttpStatus.CREATED, "PT 회원 추가에 성공하였습니다."),
    DELETE_PTMEMBER_SUCCESS(HttpStatus.OK, "PT 회원 삭제에 성공하였습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
