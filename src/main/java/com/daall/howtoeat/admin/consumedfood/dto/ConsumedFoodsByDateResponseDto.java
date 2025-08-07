package com.daall.howtoeat.admin.consumedfood.dto;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class ConsumedFoodsByDateResponseDto {
    private List<ConsumedFood> breakfast;


    private Long consumedFoodId;
    private String foodCode;
    private MealTime mealTime;
    private String foodName;
    private FoodType foodType;
    private Double kcal;
    private Double carbo;
    private Double protein;
    private Double fat;
    private Double weight;
    private String unit;
    private String providedBy;

    public ConsumedFoodsByDateResponseDto(ConsumedFood consumedFood) {
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
