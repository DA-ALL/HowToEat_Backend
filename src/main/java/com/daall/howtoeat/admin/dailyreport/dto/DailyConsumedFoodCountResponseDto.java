package com.daall.howtoeat.admin.dailyreport.dto;

import com.daall.howtoeat.domain.dailyreport.DailyReport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
public class DailyConsumedFoodCountResponseDto {
    private final LocalDate date;
    private final Long consumedFoodCount;

    public DailyConsumedFoodCountResponseDto(LocalDate date, Long consumedFoodCount) {
        this.date = date;
        this.consumedFoodCount = consumedFoodCount;
    }
}
