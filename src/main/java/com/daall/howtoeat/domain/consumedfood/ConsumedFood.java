package com.daall.howtoeat.domain.consumedfood;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodsRequestDto;
import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.favoritefood.FavoriteFood;
import com.daall.howtoeat.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "consumed_foods")
public class ConsumedFood extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_food_id")
    private FavoriteFood favoriteFood;

    @Column(nullable = false)
    private String foodCode;

    //음식 타입 : 원재료/음식/가공식품/유저 생성
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodType foodType;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private Double kcal;

    @Column(nullable = false)
    private Double carbo;

    @Column(nullable = false)
    private Double protein;

    @Column(nullable = false)
    private Double fat;

    @Column(nullable = false)
    private Double foodWeight;

    @Column(nullable = false)
    private String unit;

    //식사 타입 : 아침/점심/저녁/간식
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealTime mealTime;

    //음식 원산지 : 회사/미국산 등
    @Column(nullable = false)
    private String providedBy;

    @Column(nullable = false)
    private String source;

    @Column
    private String foodImageUrl;

    public ConsumedFood(User loginUser, ConsumedFoodsRequestDto requestDto) {
        this.user = loginUser;
        this.foodCode = requestDto.getFoodCode();
        this.foodType = requestDto.getFoodType();
        this.foodName = requestDto.getFoodName();
        this.kcal = requestDto.getKcal();
        this.carbo = requestDto.getCarbo();
        this.protein = requestDto.getProtein();
        this.fat = requestDto.getFat();
        this.foodWeight = requestDto.getFoodWeight();
        this.unit = requestDto.getUnit();
        this.mealTime = requestDto.getMealTime();
        this.providedBy = requestDto.getProvidedBy();
        this.source = requestDto.getSource();
    }

    public ConsumedFood(User loginUser, ConsumedFoodsRequestDto requestDto, String imageUrl) {
        this.user = loginUser;
        this.foodCode = requestDto.getFoodCode();
        this.foodType = requestDto.getFoodType();
        this.foodName = requestDto.getFoodName();
        this.kcal = requestDto.getKcal();
        this.carbo = requestDto.getCarbo();
        this.protein = requestDto.getProtein();
        this.fat = requestDto.getFat();
        this.foodWeight = requestDto.getFoodWeight();
        this.unit = requestDto.getUnit();
        this.mealTime = requestDto.getMealTime();
        this.providedBy = requestDto.getProvidedBy();
        this.source = requestDto.getSource();
        this.foodImageUrl = imageUrl;
    }

    public void setFavoriteFood(FavoriteFood favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

    public void updateImage(String consumedFoodImageUrl) {
        this.foodImageUrl = consumedFoodImageUrl;
    }


    public void updateFavoriteFood(FavoriteFood favoriteFood) {
        this.favoriteFood = favoriteFood;
    }
}
