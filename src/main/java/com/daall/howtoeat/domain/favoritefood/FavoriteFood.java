package com.daall.howtoeat.domain.favoritefood;

import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "favorite_foods")
public class FavoriteFood extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

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

    @Column(nullable = false)
    private String unit;

    //음식 데이터 출처 : 식약처 / 어드민 / 유저
    @Column(nullable = false)
    private String source;

    //음식 데이터 출처가 유저인 경우, 음식 설명이 있을 수 있음
    @Column
    private String description;

    @Column
    private LocalDateTime sharedAt;
}
