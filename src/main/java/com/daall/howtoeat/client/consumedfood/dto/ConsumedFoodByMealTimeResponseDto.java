package com.daall.howtoeat.client.consumedfood.dto;

import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import lombok.AllArgsConstructor;
import lombok.Getter;

//아침 점심 저녁 간식 별 날짜에 맞는 섭취한 음식
@Getter
@AllArgsConstructor
public class ConsumedFoodByMealTimeResponseDto {
    private Long consumedFoodId;
    private String foodName;
    private Double kcal;
    private Double carbo;
    private Double protein;
    private Double fat;
    private Double weight;
    private String unit;

    public ConsumedFoodByMealTimeResponseDto(ConsumedFood consumedFood) {
        this.consumedFoodId = consumedFood.getId();
        this.foodName = consumedFood.getFoodName();
        this.kcal = consumedFood.getKcal();
        this.carbo = consumedFood.getCarbo();
        this.protein = consumedFood.getProtein();
        this.fat = consumedFood.getFat();
        this.weight = consumedFood.getFoodWeight();
        this.unit = consumedFood.getUnit();
    }
}
