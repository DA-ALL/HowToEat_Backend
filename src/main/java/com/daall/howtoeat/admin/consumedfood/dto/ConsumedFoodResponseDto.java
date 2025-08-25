package com.daall.howtoeat.admin.consumedfood.dto;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import lombok.Getter;

@Getter
public class ConsumedFoodResponseDto {
    private final Long consumedFoodId;
    private final String foodCode;
    private final MealTime mealTime;
    private final String foodName;
    private final FoodType foodType;
    private final Double kcal;
    private final Double carbo;
    private final Double protein;
    private final Double fat;
    private final Double weight;
    private final String unit;
    private final String providedBy;

    public ConsumedFoodResponseDto(ConsumedFood consumedFood) {
        this.consumedFoodId = consumedFood.getId();
        this.foodCode = consumedFood.getFoodCode();
        this.mealTime = consumedFood.getMealTime();
        this.foodName = consumedFood.getFoodName();
        this.foodType = consumedFood.getFoodType();
        this.kcal = consumedFood.getKcal();
        this.carbo = consumedFood.getCarbo();
        this.protein = consumedFood.getProtein();
        this.fat = consumedFood.getFat();
        this.weight = consumedFood.getFoodWeight();
        this.unit = consumedFood.getUnit();
        this.providedBy = consumedFood.getProvidedBy();
    }
}
