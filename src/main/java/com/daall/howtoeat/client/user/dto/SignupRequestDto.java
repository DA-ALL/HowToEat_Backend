package com.daall.howtoeat.client.user.dto;

import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.UserActivityLevel;
import com.daall.howtoeat.common.enums.UserGoal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupRequestDto {
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식을 입력해 주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해 주세요.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,}$",
            message = "이름은 숫자나 특수문자를 포함할 수 없으며, 최소 1글자 이상이어야 합니다.")
    private String name;

    private LocalDate birthday;  // yyyy-MM-dd 형식
    private Gender gender;
    private Double height;
    private Double weight;
    private UserGoal goal;
    private UserActivityLevel activityLevel;
    private boolean isNextGym;
}
