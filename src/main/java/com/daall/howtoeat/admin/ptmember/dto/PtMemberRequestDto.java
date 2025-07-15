package com.daall.howtoeat.admin.ptmember.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PtMemberRequestDto {
    @NotNull(message = "트레이너 id가 없습니다. 다시시도해주세요.")
    private Long trainerId;
    @NotNull(message = "유저 id가 없습니다. 다시시도해주세요.")
    private Long userId;
}
