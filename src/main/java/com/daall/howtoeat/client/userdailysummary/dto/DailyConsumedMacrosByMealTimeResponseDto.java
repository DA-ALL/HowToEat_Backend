package com.daall.howtoeat.client.userdailysummary.dto;

import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.user.UserDailySummary;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.Getter;

/**
 * 하루 중 특정 끼니 및 날짜 대한 목표 및 섭취 탄수화물, 단백질, 지방 정보 DTO
 */
@Getter
public class DailyConsumedMacrosByMealTimeResponseDto {
    private MealTime mealTime;

    private Double targetCarbo;
    private Double targetProtein;
    private Double targetFat;

    private Double consumedCarbo;
    private Double consumedProtein;
    private Double consumedFat;

    public DailyConsumedMacrosByMealTimeResponseDto(UserDailySummary userDailySummary, MealTime mealTime) {
        this.mealTime = mealTime;

        switch (mealTime) {
            case BREAKFAST -> {
                this.targetCarbo = round(userDailySummary.getUserTarget().getCarbo() * 0.3);
                this.targetProtein = round(userDailySummary.getUserTarget().getProtein() * 0.3);
                this.targetFat = round(userDailySummary.getUserTarget().getFat() * 0.3);

                this.consumedCarbo = round(userDailySummary.getBreakfastCarbo());
                this.consumedProtein = round(userDailySummary.getBreakfastProtein());
                this.consumedFat = round(userDailySummary.getBreakfastFat());
            }
            case LUNCH -> {
                this.targetCarbo = round(userDailySummary.getUserTarget().getCarbo() * 0.4);
                this.targetProtein = round(userDailySummary.getUserTarget().getProtein() * 0.4);
                this.targetFat = round(userDailySummary.getUserTarget().getFat() * 0.4);

                this.consumedCarbo = round(userDailySummary.getLunchCarbo());
                this.consumedProtein = round(userDailySummary.getLunchProtein());
                this.consumedFat = round(userDailySummary.getLunchFat());
            }
            case DINNER -> {
                this.targetCarbo = round(userDailySummary.getUserTarget().getCarbo() * 0.3);
                this.targetProtein = round(userDailySummary.getUserTarget().getProtein() * 0.3);
                this.targetFat = round(userDailySummary.getUserTarget().getFat() * 0.3);

                this.consumedCarbo = round(userDailySummary.getDinnerCarbo());
                this.consumedProtein = round(userDailySummary.getDinnerProtein());
                this.consumedFat = round(userDailySummary.getDinnerFat());
            }
            case SNACK -> {
                this.targetCarbo = round(userDailySummary.getUserTarget().getCarbo() * 0.2);
                this.targetProtein = round(userDailySummary.getUserTarget().getProtein() * 0.2);
                this.targetFat = round(userDailySummary.getUserTarget().getFat() * 0.2);

                this.consumedCarbo = round(userDailySummary.getSnackCarbo());
                this.consumedProtein = round(userDailySummary.getSnackProtein());
                this.consumedFat = round(userDailySummary.getSnackFat());
            }
        }
    }

    public DailyConsumedMacrosByMealTimeResponseDto(UserTarget target, MealTime mealTime) {
        this.mealTime = mealTime;

        switch (mealTime) {
            case BREAKFAST -> {
                this.targetCarbo = round(target.getCarbo() * 0.3);
                this.targetProtein = round(target.getProtein() * 0.3);
                this.targetFat = round(target.getFat() * 0.3);
            }
            case LUNCH -> {
                this.targetCarbo = round(target.getCarbo() * 0.4);
                this.targetProtein = round(target.getProtein() * 0.4);
                this.targetFat = round(target.getFat() * 0.4);
            }
            case DINNER -> {
                this.targetCarbo = round(target.getCarbo() * 0.3);
                this.targetProtein = round(target.getProtein() * 0.3);
                this.targetFat = round(target.getFat() * 0.3);
            }
            case SNACK -> {
                this.targetCarbo = round(target.getCarbo() * 0.2);
                this.targetProtein = round(target.getProtein() * 0.2);
                this.targetFat = round(target.getFat() * 0.2);
            }
        }

        this.consumedCarbo = 0.0;
        this.consumedProtein = 0.0;
        this.consumedFat = 0.0;
    }

    //부동소수점 방지 - 소수점 둘째 자리에서 반올림
    private double round(double value) {
        return Math.round(value * 10) / 10.0;
    }
}
