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

    //target은 BREAKFAST 3 : LUNCH 4 : DINNER 4 로 나누기 위해 필요
    private Double targetCarbo;
    private Double targetProtein;
    private Double targetFat;

    private Double consumedCarbo;
    private Double consumedProtein;
    private Double consumedFat;

    //만약 한끼라도 등록 했을 경우
    public DailyConsumedMacrosByMealTimeResponseDto(UserDailySummary userDailySummary, MealTime mealTime) {
        this.mealTime = mealTime;

        switch (mealTime) {
            case BREAKFAST -> {
                this.targetCarbo = (double) Math.round(userDailySummary.getUserTarget().getCarbo() * 0.3);
                this.targetProtein = (double) Math.round(userDailySummary.getUserTarget().getProtein() * 0.3);
                this.targetFat = (double) Math.round(userDailySummary.getUserTarget().getFat() * 0.3);

                this.consumedCarbo = (double) Math.round(userDailySummary.getBreakfastCarbo());
                this.consumedProtein = (double) Math.round(userDailySummary.getBreakfastProtein());
                this.consumedFat = (double) Math.round(userDailySummary.getBreakfastFat());
            }
            case LUNCH -> {
                this.targetCarbo = (double) Math.round(userDailySummary.getUserTarget().getCarbo() * 0.4);
                this.targetProtein = (double) Math.round(userDailySummary.getUserTarget().getProtein() * 0.4);
                this.targetFat = (double) Math.round(userDailySummary.getUserTarget().getFat() * 0.4);

                this.consumedCarbo = (double) Math.round(userDailySummary.getLunchCarbo());
                this.consumedProtein = (double) Math.round(userDailySummary.getLunchProtein());
                this.consumedFat = (double) Math.round(userDailySummary.getLunchFat());
            }
            case DINNER -> {
                this.targetCarbo = (double) Math.round(userDailySummary.getUserTarget().getCarbo() * 0.3);
                this.targetProtein = (double) Math.round(userDailySummary.getUserTarget().getProtein() * 0.3);
                this.targetFat = (double) Math.round(userDailySummary.getUserTarget().getFat() * 0.3);

                this.consumedCarbo = (double) Math.round(userDailySummary.getDinnerCarbo());
                this.consumedProtein = (double) Math.round(userDailySummary.getDinnerProtein());
                this.consumedFat = (double) Math.round(userDailySummary.getDinnerFat());
            }
            case SNACK -> {
                this.targetCarbo = (double) Math.round(userDailySummary.getUserTarget().getCarbo() * 0.2);
                this.targetProtein = (double) Math.round(userDailySummary.getUserTarget().getProtein() * 0.2);
                this.targetFat = (double) Math.round(userDailySummary.getUserTarget().getFat() * 0.2);

                this.consumedCarbo = (double) Math.round(userDailySummary.getSnackCarbo());
                this.consumedProtein = (double) Math.round(userDailySummary.getSnackProtein());
                this.consumedFat = (double) Math.round(userDailySummary.getSnackFat());
            }
        }
    }

    //만약 한끼라도 등록하지 않았을 경우
    public DailyConsumedMacrosByMealTimeResponseDto(UserTarget target, MealTime mealTime) {
        this.mealTime = mealTime;

        switch (mealTime) {
            case BREAKFAST -> {
                this.targetCarbo = (double) Math.round(target.getCarbo() * 0.3);
                this.targetProtein = (double) Math.round(target.getProtein() * 0.3);
                this.targetFat = (double) Math.round(target.getFat() * 0.3);

                this.consumedCarbo = 0.0;
                this.consumedProtein = 0.0;
                this.consumedFat = 0.0;
            }
            case LUNCH -> {
                this.targetCarbo = (double) Math.round(target.getCarbo() * 0.4);
                this.targetProtein = (double) Math.round(target.getProtein() * 0.4);
                this.targetFat = (double) Math.round(target.getFat() * 0.4);

                this.consumedCarbo = 0.0;
                this.consumedProtein = 0.0;
                this.consumedFat = 0.0;
            }
            case DINNER -> {
                this.targetCarbo = (double) Math.round(target.getCarbo() * 0.3);
                this.targetProtein = (double) Math.round(target.getProtein() * 0.3);
                this.targetFat = (double) Math.round(target.getFat() * 0.3);

                this.consumedCarbo = 0.0;
                this.consumedProtein = 0.0;
                this.consumedFat = 0.0;
            }
            case SNACK -> {
                this.targetCarbo = (double) Math.round(target.getCarbo() * 0.2);
                this.targetProtein = (double) Math.round(target.getProtein() * 0.2);
                this.targetFat = (double) Math.round(target.getFat() * 0.2);

                this.consumedCarbo = 0.0;
                this.consumedProtein = 0.0;
                this.consumedFat = 0.0;
            }
        }
    }
}
