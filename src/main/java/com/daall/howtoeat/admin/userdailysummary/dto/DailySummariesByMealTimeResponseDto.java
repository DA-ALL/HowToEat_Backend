package com.daall.howtoeat.admin.userdailysummary.dto;

import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosByMealTimeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DailySummariesByMealTimeResponseDto {
    private DailyConsumedMacrosByMealTimeResponseDto breakfastDailySummary;
    private DailyConsumedMacrosByMealTimeResponseDto lunchDailySummary;
    private DailyConsumedMacrosByMealTimeResponseDto dinnerDailySummary;
    private DailyConsumedMacrosByMealTimeResponseDto snackDailySummary;
}
