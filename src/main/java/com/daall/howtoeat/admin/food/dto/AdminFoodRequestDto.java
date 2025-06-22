package com.daall.howtoeat.admin.food.dto;

import com.daall.howtoeat.common.enums.FoodType;
import lombok.Getter;

@Getter
public class AdminFoodRequestDto {
    private FoodType foodType;
    private String foodName;
    private String providedBy;
    private Double kcal;
    private Double carbo;
    private Double protein;
    private Double fat;
    private Double foodWeight;
    private String unit;
    private Boolean isPerServing;
    private Boolean isRecommended;
}
