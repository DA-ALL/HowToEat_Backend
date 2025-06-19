package com.daall.howtoeat.client.consumedfood.dto;

import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import lombok.Getter;

@Getter
public class ConsumedFoodDetailResponseDto {
    private Long consumedFoodId;
    private String foodName;
    private Double kcal;
    private Double carbo;
    private Double protein;
    private Double fat;
    private Double weight;
    private String unit;
    private String providedBy;
    private String foodImageUrl;

    public ConsumedFoodDetailResponseDto(ConsumedFood consumedFood) {
        this.consumedFoodId = consumedFood.getId();
        this.foodName = consumedFood.getFoodName();
        this.kcal = consumedFood.getKcal();
        this.carbo = consumedFood.getCarbo();
        this.protein = consumedFood.getProtein();
        this.fat = consumedFood.getFat();
        this.weight = consumedFood.getFoodWeight();
        this.unit = consumedFood.getUnit();
        this.providedBy = consumedFood.getProvidedBy();
        this.foodImageUrl = consumedFood.getFoodImageUrl();
    }
}
