package com.daall.howtoeat.admin.favoritefood.dto;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class AdminFavoriteFoodResponseDto {
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

    public AdminFavoriteFoodResponseDto(FavoriteFood favoriteFood) {
        this.id = favoriteFood.getId();
        this.foodName = favoriteFood.getFoodName();
        this.foodCode = favoriteFood.getFoodCode();
        this.foodType = favoriteFood.getFoodType();
        this.providedBy = favoriteFood.getProvidedBy();
        this.kcal = favoriteFood.getKcal();
        this.carbo = favoriteFood.getCarbo();
        this.protein = favoriteFood.getProtein();
        this.fat = favoriteFood.getFat();
        this.foodWeight = favoriteFood.getFoodWeight();
        this.unit = favoriteFood.getUnit();
        this.description = favoriteFood.getDescription();
        this.sharedAt= favoriteFood.getSharedAt();
    }
}
