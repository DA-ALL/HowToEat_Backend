package com.daall.howtoeat.admin.favoritefood.dto;

import com.daall.howtoeat.common.enums.FoodType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AdminFavoriteFoodWithUserResponseDto {
    private final Long id;
    private final String foodName;
    private final String foodCode;
    private final FoodType foodType;
    private final String providedBy;
    private final Double kcal;
    private final Double carbo;
    private final Double protein;
    private final Double fat;
    private final Double foodWeight;
    private final String unit;
    private final String description;
    private final LocalDateTime sharedAt;

    private final Long userId;
    private final String userName;
    private final String profileImageUrl;
}
