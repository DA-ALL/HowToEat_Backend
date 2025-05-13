package com.daall.howtoeat.consumedfood;

import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column(unique = true, nullable = false)
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

    //식사 타입 : 아침/점심/저녁/간식
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealTime mealTime;

    //음식 원산지 : 회사/미국산 등
    @Column(nullable = false)
    private String provided_by;

    @Column
    private String foodImageUrl;
}
