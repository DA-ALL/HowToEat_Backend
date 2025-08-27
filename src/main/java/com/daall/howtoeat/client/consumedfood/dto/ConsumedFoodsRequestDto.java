package com.daall.howtoeat.client.consumedfood.dto;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.common.enums.MealTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ConsumedFoodsRequestDto {
    @NotBlank(message = "음식 코드가 비어있습니다. 다시 시도해주세요.")
    private String foodCode;

    private Long favoriteFoodId;

    @NotBlank(message = "음식 이름이 비어있습니다. 다시 시도해주세요.")
    private String foodName;

    @NotNull(message = "식사 시간이 비어있습니다. 다시 시도해주세요.")
    private MealTime mealTime;

    @NotNull(message = "음식 중량이 비어있습니다. 다시 시도해주세요.")
    private Double foodWeight;

    @NotNull(message = "음식 종류가 비어있습니다. 다시 시도해주세요.")
    private FoodType foodType;

    @NotNull(message = "칼로리 정보가 비어있습니다. 다시 시도해주세요.")
    private Double kcal;

    @NotNull(message = "탄수화물 정보가 비어있습니다. 다시 시도해주세요.")
    private Double carbo;

    @NotNull(message = "단백질 정보가 비어있습니다. 다시 시도해주세요.")
    private Double protein;

    @NotNull(message = "지방 정보가 비어있습니다. 다시 시도해주세요.")
    private Double fat;

    private String providedBy;

    @NotNull(message = "1회 제공량 여부 정보가 비어있습니다. 다시 시도해주세요.")
    private Boolean isPerServing;

    @NotBlank(message = "단위 정보가 비어있습니다. 다시 시도해주세요.")
    private String unit;

    @NotBlank(message = "단위 정보가 비어있습니다. 다시 시도해주세요.")
    private String source;

    @NotNull(message = "날짜가 비어있습니다. 다시 시도해주세요.")
    private LocalDate date;
}
