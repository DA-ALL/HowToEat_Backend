package com.daall.howtoeat.client.food.dto;

import com.daall.howtoeat.domain.food.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.SQLOutput;

@Getter
public class FoodResponseDto {
    private Long foodId;
    private String foodCode;
    private String foodName;
    private Double foodWeight;
    private Double kcal;
    private String type;
    private Double carbo;
    private Double protein;
    private Double fat;
    private String providedBy;
    private String source;
    private Boolean isPerServing;
    private String unit;

    public FoodResponseDto(Food food) {
        this.foodId = food.getId();
        this.foodCode = food.getFoodCode();
        this.foodName = food.getFoodName();
        this.foodWeight = food.getFoodWeight();
        this.kcal = food.getKcal();
        this.type = food.getFoodType().name();
        this.carbo = food.getCarbo();
        this.protein = food.getProtein();
        this.fat = food.getFat();
        this.providedBy = food.getProvidedBy();
        this.source = food.getSource();
        this.isPerServing = food.getIsPerServing();
        this.unit = food.getUnit();
    }
}
