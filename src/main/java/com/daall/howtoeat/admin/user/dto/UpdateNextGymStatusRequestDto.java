package com.daall.howtoeat.admin.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateNextGymStatusRequestDto {
    @NotNull(message = "헬스장 회원 여부가 없습니다. 다시 시도해주세요.")
    private Boolean isNextGym;
}
