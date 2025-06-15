package com.daall.howtoeat.client.favoritefood.dto;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import lombok.Getter;

@Getter
public class FavoriteFoodResponseDto {
    private Long favoriteFoodId;
    private String foodCode;
    private String foodName;
    private Double foodWeight;
    private FoodType foodType;
    private Double kcal;
    private Double carbo;
    private Double protein;
    private Double fat;
    private String providedBy;
    private String unit;
    private String foodImageUrl;

    public FavoriteFoodResponseDto(FavoriteFood favoriteFood) {
        this.favoriteFoodId = favoriteFood.getId();
        this.foodCode = favoriteFood.getFoodCode();
        this.foodName = favoriteFood.getFoodName();
        this.foodWeight = favoriteFood.getFoodWeight();
        this.foodType = favoriteFood.getFoodType();
        this.kcal = favoriteFood.getKcal();
        this.carbo = favoriteFood.getCarbo();
        this.protein = favoriteFood.getProtein();
        this.fat = favoriteFood.getFat();
        this.providedBy = favoriteFood.getProvidedBy();
        this.unit = favoriteFood.getUnit();
        this.foodImageUrl = favoriteFood.getFoodImageUrl();

    }

}
