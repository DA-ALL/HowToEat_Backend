package com.daall.howtoeat.domain.food;

import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.common.enums.FoodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "foods")
public class Food extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String foodCode;

    //음식 타입 : 원재료/음식/가공식품/유저 생성
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodType foodType;

    @Column(nullable = false)
    private String foodName;

    //대표음식명 : 소고기 입력시 한우볶음밥이 나올 수 있도록 하기 위한 컬럼
    @Column
    private String representative_name;

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

    //음식 원산지 : 회사/미국산 등
    @Column(nullable = false)
    private String provided_by;

    //음식 데이터 출처 : 식약처 / 어드민 / 유저
    @Column(nullable = false)
    private String source;

    //1인분인지 아닌지 판별
    @Column(nullable = false)
    private Boolean isPerServing;

    //음식이 몇번 선택됐는지 -> 추후 인기있는 음식들을 추천 음식에 활용
    @Column
    private Long selectedCount;
}
