package com.daall.howtoeat.client.userstat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserHeightRequestDto {
    @NotNull(message = "키를 입력해주세요.")
    private double height;
}
