package com.daall.howtoeat.client.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
//    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;

//    @NotBlank(message = "비밀번호를 입력해 주세요.")
    private String password;
}