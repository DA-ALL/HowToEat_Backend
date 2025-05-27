package com.daall.howtoeat.client.consumedfood.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//아침 점심 저녁 간식 별 날짜에 맞는 섭취한 음식
@Getter
@AllArgsConstructor
public class ConsumedFoodByMealTimeResponseDto {
    private Long consumedFoodId;
    private String foodName;
    private Double kcal;
    private Double weigh;
    private String unit;
}
