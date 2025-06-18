package com.daall.howtoeat.client.userdailysummary.dto;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodsRequestDto;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class DailyNutritionSummary {
    private double totalKcal;
    private double totalCarbo;
    private double totalProtein;
    private double totalFat;

    private double breakfastKcal;
    private double breakfastCarbo;
    private double breakfastProtein;
    private double breakfastFat;

    private double lunchKcal;
    private double lunchCarbo;
    private double lunchProtein;
    private double lunchFat;

    private double dinnerKcal;
    private double dinnerCarbo;
    private double dinnerProtein;
    private double dinnerFat;

    private double snackKcal;
    private double snackCarbo;
    private double snackProtein;
    private double snackFat;


    // 모든 끼니별 섭취음식 값 계산
    public DailyNutritionSummary(List<ConsumedFood> consumedFoodList) {

        for(ConsumedFood consumedFood : consumedFoodList) {
            System.out.println(consumedFood.toString());
            MealTime mealTime = consumedFood.getMealTime();

            this.totalKcal = round(this.totalKcal + consumedFood.getKcal());
            this.totalCarbo = round(this.totalCarbo + consumedFood.getCarbo());
            this.totalProtein = round(this.totalProtein + consumedFood.getProtein());
            this.totalFat = round(this.totalFat + consumedFood.getFat());

            switch (mealTime) {
                case BREAKFAST -> {
                    this.breakfastKcal = round(this.totalKcal + consumedFood.getKcal());
                    this.breakfastCarbo = round(this.breakfastCarbo + consumedFood.getCarbo());
                    this.breakfastProtein = round(this.breakfastProtein + consumedFood.getProtein());
                    this.breakfastFat = round(this.breakfastFat + consumedFood.getFat());
                }
                case LUNCH -> {
                    this.lunchKcal = round(this.lunchKcal + consumedFood.getKcal());
                    this.lunchCarbo = round(this.lunchCarbo + consumedFood.getCarbo());
                    this.lunchProtein = round(this.lunchProtein + consumedFood.getProtein());
                    this.lunchFat = round(this.lunchFat + consumedFood.getFat());
                }
                case DINNER -> {
                    this.dinnerKcal = round(this.dinnerKcal + consumedFood.getKcal());
                    this.dinnerCarbo = round(this.dinnerCarbo + consumedFood.getCarbo());
                    this.dinnerProtein = round(this.dinnerProtein + consumedFood.getProtein());
                    this.dinnerFat = round(this.dinnerFat + consumedFood.getFat());
                }
                case SNACK -> {
                    this.snackKcal = round(this.snackKcal + consumedFood.getKcal());
                    this.snackCarbo = round(this.snackCarbo + consumedFood.getCarbo());
                    this.snackProtein = round(this.snackProtein + consumedFood.getProtein());
                    this.snackFat = round(this.snackFat + consumedFood.getFat());
                }
            }
        }
    }

    //부동소수점 방지 - 소수점 둘째 자리에서 반올림
    private double round(double value) {
        return Math.round(value * 10) / 10.0;
    }
}
