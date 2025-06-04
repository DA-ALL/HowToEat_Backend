package com.daall.howtoeat.client.food.dto;

import com.daall.howtoeat.domain.food.Food;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
    private Boolean isPerServing;
    private String unit;

    public static FoodResponseDto from(Food food) {
        return new FoodResponseDto(
                food.getId(),
                food.getFoodCode(),
                food.getFoodName(),
                food.getFoodWeight(),
                food.getKcal(),
                food.getFoodType().name(),
                food.getCarbo(),
                food.getProtein(),
                food.getFat(),
                food.getProvided_by(),
                food.getIsPerServing(),
                food.getUnit()
        );
    }
}
