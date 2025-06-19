package com.daall.howtoeat.client.favoritefood.dto;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.common.enums.MealTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


// 검색을 통해 즐겨찾기에 추가한 경우 사용하는 DTO
// * 직접 음식을 생성하는 경우에는 description과 foodCode가 없기 때문에 분리
@Setter
@Getter
public class FavoriteFoodAddByConsumedFoodRequestDto {
    @NotNull(message = "섭취 음식 ID가 비어있습니다. 다시 시도해주세요.")
    private Long consumedFoodId;

    @NotBlank(message = "음식 이름이 비어있습니다. 다시 시도해주세요.")
    private String foodName;

    @NotBlank(message = "음식 코드가 비어있습니다. 다시 시도해주세요.")
    private String foodCode;

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

    @NotBlank(message = "제공처 정보가 비어있습니다. 다시 시도해주세요.")
    private String providedBy;

    @NotNull(message = "제공처 정보가 비어있습니다. 다시 시도해주세요.")
    private Boolean isPerServing;

    @NotBlank(message = "단위 정보가 비어있습니다. 다시 시도해주세요.")
    private String unit;

    @NotBlank(message = "단위 정보가 비어있습니다. 다시 시도해주세요.")
    private String source;

}
