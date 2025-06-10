package com.daall.howtoeat.client.userdailysummary.dto;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodsRequestDto;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import lombok.Getter;

import java.util.List;

@Getter
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
            MealTime mealTime = consumedFood.getMealTime();

            this.totalKcal += consumedFood.getKcal();
            this.totalCarbo += consumedFood.getCarbo();
            this.totalProtein += consumedFood.getProtein();
            this.totalFat += consumedFood.getFat();

            switch (mealTime) {
                case BREAKFAST -> {
                    this.breakfastKcal += consumedFood.getKcal();
                    this.breakfastCarbo += consumedFood.getCarbo();
                    this.breakfastProtein += consumedFood.getProtein();
                    this.breakfastFat += consumedFood.getFat();
                }
                case LUNCH -> {
                    this.lunchKcal += consumedFood.getKcal();
                    this.lunchCarbo += consumedFood.getCarbo();
                    this.lunchProtein += consumedFood.getProtein();
                    this.lunchFat += consumedFood.getFat();
                }
                case DINNER -> {
                    this.dinnerKcal += consumedFood.getKcal();
                    this.dinnerCarbo += consumedFood.getCarbo();
                    this.dinnerProtein += consumedFood.getProtein();
                    this.dinnerFat += consumedFood.getFat();
                }
                case SNACK -> {
                    this.snackKcal += consumedFood.getKcal();
                    this.snackCarbo += consumedFood.getCarbo();
                    this.snackProtein += consumedFood.getProtein();
                    this.snackFat += consumedFood.getFat();
                }
            }
        }


    }
}
