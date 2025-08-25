package com.daall.howtoeat.admin.userdailysummary.dto;

import com.daall.howtoeat.domain.user.UserDailySummary;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class DailyCaloriesSummaryDto {
    private final Long id;
    private final Double totalKcal;
    private final Double breakfastKcal;
    private final Double lunchKcal;
    private final Double dinnerKcal;
    private final Double snackKcal;
    private final LocalDate createdAt;

    public DailyCaloriesSummaryDto(UserDailySummary userDailySummary) {
        this.id = userDailySummary.getId();
        this.totalKcal = userDailySummary.getTotalKcal();
        this.breakfastKcal = userDailySummary.getBreakfastKcal();
        this.lunchKcal = userDailySummary.getLunchKcal();
        this.dinnerKcal = userDailySummary.getDinnerKcal();
        this.snackKcal = userDailySummary.getSnackKcal();
        this.createdAt = userDailySummary.getCreatedAt().toLocalDate();
    }
}
