package com.daall.howtoeat.admin.consumedfood.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class ConsumedFoodsByDateResponseDto {
    private final List<ConsumedFoodResponseDto> breakfastConsumedFoods;
    private final List<ConsumedFoodResponseDto> lunchConsumedFoods;
    private final List<ConsumedFoodResponseDto> dinnerConsumedFoods;
    private final List<ConsumedFoodResponseDto> snackConsumedFoods;

    public ConsumedFoodsByDateResponseDto(List<ConsumedFoodResponseDto> breakfastConsumedFoods, List<ConsumedFoodResponseDto> lunchConsumedFoods, List<ConsumedFoodResponseDto> dinnerConsumedFoods, List<ConsumedFoodResponseDto> snackConsumedFoods) {
        this.breakfastConsumedFoods = breakfastConsumedFoods;
        this.lunchConsumedFoods = lunchConsumedFoods;
        this.dinnerConsumedFoods = dinnerConsumedFoods;
        this.snackConsumedFoods = snackConsumedFoods;
    }
}
