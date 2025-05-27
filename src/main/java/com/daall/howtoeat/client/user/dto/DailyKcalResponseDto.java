package com.daall.howtoeat.client.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DailyKcalResponseDto {
    private LocalDate date;
    private Double targetKcal;
    private Double consumedKcal;
}
