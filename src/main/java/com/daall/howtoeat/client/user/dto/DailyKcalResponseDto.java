package com.daall.howtoeat.client.user.dto;

import com.daall.howtoeat.domain.user.UserDailySummary;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
public class DailyKcalResponseDto {
    private LocalDate date;
    private Double targetKcal;
    private Double consumedKcal;
}
