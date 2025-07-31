package com.daall.howtoeat.client.recommendfood.dto;

import com.daall.howtoeat.domain.food.Food;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RecommendFoodResponseDto {
    private final Food food;
}
