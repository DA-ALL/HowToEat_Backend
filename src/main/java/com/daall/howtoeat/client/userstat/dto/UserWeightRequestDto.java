package com.daall.howtoeat.client.userstat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserWeightRequestDto {
    @NotNull(message = "몸무게를 입력해주세요.")
    private double weight;
}
