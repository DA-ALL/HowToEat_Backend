package com.daall.howtoeat.admin.gym.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class GymRequestDto {
    @NotBlank(message = "헬스장 이름을 입력해주세요.")
    private String name;
}
