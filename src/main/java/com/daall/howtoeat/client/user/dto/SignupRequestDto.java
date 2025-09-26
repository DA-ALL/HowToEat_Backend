package com.daall.howtoeat.client.user.dto;

import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.SignupProvider;
import com.daall.howtoeat.common.enums.UserActivityLevel;
import com.daall.howtoeat.common.enums.UserGoal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class SignupRequestDto {
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식을 입력해 주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해 주세요.")
//    @Pattern(regexp = "^[a-zA-Z가-힣]{1,}$",
//            message = "이름은 숫자나 특수문자를 포함할 수 없으며, 최소 1글자 이상이어야 합니다.")
    private String name;

    private LocalDate birthday;  // yyyy-MM-dd 형식
    private Gender gender;
    private Double height;
    private Double weight;
    private UserGoal goal;
    private UserActivityLevel activityLevel;
    private Boolean isNextGym;
    @NotNull(message = "signup provider를 입력해 주세요.")
    private SignupProvider signupProvider;
    private String profileImageUrl;

    @NotNull(message = "이용약관에 동의하지 않으면 회원가입이 불가능합니다.")
    private LocalDateTime termsAgreedAt;

    @NotNull(message = "개인정보 처리에 동의하지 않으면 회원가입이 불가능합니다.")
    private LocalDateTime privacyAgreedAt;

    @NotNull(message = "에러가 발생했습니다.")
    private LocalTime breakfastTime;

    @NotNull(message = "에러가 발생했습니다.")
    private LocalTime lunchTime;

    @NotNull(message = "에러가 발생했습니다.")
    private LocalTime dinnerTime;

    // admin 계정생성에
    public SignupRequestDto(String name) {
        this.email = "admin";
        this.name = name;
        this.birthday = LocalDate.of(1995,1,1);
        this.gender = Gender.MALE;
        this.height = 1.0;
        this.weight = 1.0;
        this.goal = UserGoal.LOSE_WEIGHT;
        this.activityLevel = UserActivityLevel.LOW;
        this.isNextGym = false;
        this.signupProvider = SignupProvider.ADMIN;
        this.profileImageUrl = "/administrate/images/icon_human_red.png";
        //어드민 계정 생성은 일단 동의 처리
        this.termsAgreedAt = LocalDateTime.now();
        this.privacyAgreedAt = LocalDateTime.now();
        this.breakfastTime = LocalTime.of(7, 0);
        this.lunchTime = LocalTime.of(13, 0);
        this.dinnerTime = LocalTime.of(20, 0);

    }
}
