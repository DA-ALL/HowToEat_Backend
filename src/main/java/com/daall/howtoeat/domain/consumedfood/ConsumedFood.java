package com.daall.howtoeat.domain.consumedfood;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodsRequestDto;
import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.common.enums.MealTime;
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
    private String provided_by;

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
        this.provided_by = requestDto.getProvidedBy();
        this.foodImageUrl = requestDto.getFoodCode();
    }
}
