package com.daall.howtoeat.client.userdailysummary.dto;

import com.daall.howtoeat.domain.user.UserDailySummary;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DailyConsumedMacrosResponseDto {
    private LocalDate date;
    private Double targetKcal;
    private Double targetCarbo;
    private Double targetProtein;
    private Double targetFat;

    private Double consumedKcal;
    private Double consumedCarbo;
    private Double consumedProtein;
    private Double consumedFat;

    private Double breakfastKcal;
    private Double lunchKcal;
    private Double dinnerKcal;
    private Double snackKcal;

    public DailyConsumedMacrosResponseDto(UserDailySummary userDailySummary) {
        this.date = userDailySummary.getCreatedAt().toLocalDate();
        this.targetKcal = userDailySummary.getUserTarget().getKcal();
        this.targetCarbo = userDailySummary.getUserTarget().getCarbo();
        this.targetProtein = userDailySummary.getUserTarget().getProtein();
        this.targetFat = userDailySummary.getUserTarget().getFat();
        this.consumedKcal = userDailySummary.getTotalKcal();
        this.consumedCarbo = userDailySummary.getTotalCarbo();
        this.consumedProtein = userDailySummary.getTotalProtein();
        this.consumedFat = userDailySummary.getTotalFat();
        this.breakfastKcal = userDailySummary.getBreakfastKcal();
        this.lunchKcal = userDailySummary.getLunchKcal();
        this.dinnerKcal = userDailySummary.getDinnerKcal();
        this.snackKcal = userDailySummary.getSnackKcal();
    }

    public DailyConsumedMacrosResponseDto(UserTarget target, LocalDate date) {
        this.date = date;
        this.targetKcal = target.getKcal();
        this.targetCarbo = target.getCarbo();
        this.targetFat = target.getFat();
        this.targetProtein = target.getProtein();

        this.consumedKcal = 0.0;
        this.consumedCarbo = 0.0;
        this.consumedProtein = 0.0;
        this.consumedFat = 0.0;

        this.breakfastKcal = 0.0;
        this.lunchKcal = 0.0;
        this.dinnerKcal = 0.0;
        this.snackKcal = 0.0;
    }
}
