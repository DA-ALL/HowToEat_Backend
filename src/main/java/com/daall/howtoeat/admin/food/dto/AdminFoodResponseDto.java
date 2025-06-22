package com.daall.howtoeat.admin.food.dto;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.domain.food.Food;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AdminFoodResponseDto {
    private final Long id;
    private final String foodName;
    private final String foodCode;
    private final FoodType foodType;
    private final String representativeName;
    private final Double kcal;
    private final Double carbo;
    private final Double protein;
    private final Double fat;
    private final Double foodWeight;
    private final String unit;
    private final Boolean isPerServing;
    private final Boolean isRecommended;
}
