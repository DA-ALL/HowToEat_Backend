package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "올바른 이메일 형식을 입력해 주세요.")
    private String email;

    @NotBlank(message = "이름을 입력해 주세요.")
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,}$",
            message = "이름은 숫자나 특수문자를 포함할 수 없으며, 최소 1글자 이상이어야 합니다.")
    private String name;

    private String birthday;  // yyyy-MM-dd 형식
    private Gender gender;
    private Double height;
    private Double weight;
    private String goal;
    private String activityLevel;
    private boolean isNextGym;
}
