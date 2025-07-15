package com.daall.howtoeat.client.favoritefood.dto;

import com.daall.howtoeat.common.enums.FoodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

// 직접 음식을 생성해서 즐겨찾기에 추가한 경우 사용하는 DTO
// * 직접 음식을 생성하는 경우에는 description과 foodCode가 없기 때문에 분리
@Getter
public class FavoriteFoodAddByNewRequestDto {
    @NotBlank(message = "음식 이름이 비어있습니다. 다시 시도해주세요.")
    private String foodName;

    @NotNull(message = "칼로리 정보가 비어있습니다. 다시 시도해주세요.")
    private Double kcal;

    @NotNull(message = "음식 종류가 비어있습니다. 다시 시도해주세요.")
    private FoodType foodType;

    @NotNull(message = "탄수화물 정보가 비어있습니다. 다시 시도해주세요.")
    private Double carbo;

    @NotNull(message = "단백질 정보가 비어있습니다. 다시 시도해주세요.")
    private Double protein;

    @NotNull(message = "지방 정보가 비어있습니다. 다시 시도해주세요.")
    private Double fat;

    @NotNull(message = "음식 중량이 비어있습니다. 다시 시도해주세요.")
    private Double foodWeight;

    @NotBlank(message = "음식 중량이 비어있습니다. 다시 시도해주세요.")
    private String providedBy;

    @NotBlank(message = "단위 정보가 비어있습니다. 다시 시도해주세요.")
    private String unit;

    @NotBlank(message = "데이터 출처가 비어있습니다. 다시 시도해주세요.")
    private String source;

    private String description;
}
