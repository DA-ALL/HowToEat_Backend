package com.daall.howtoeat.admin.dailyreport.dto;

import com.daall.howtoeat.domain.dailyreport.DailyReport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
public class DailyConsumedFoodCountResponseDto {
    private final LocalDate date;
    private final Long consumedFoodCount;

    public DailyConsumedFoodCountResponseDto(DailyReport dailyReport) {
        this.date = dailyReport.getCreatedAt().toLocalDate();
        this.consumedFoodCount = dailyReport.getTodayConsumedFoodCount();
    }
}
