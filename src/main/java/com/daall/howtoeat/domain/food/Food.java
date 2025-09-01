package com.daall.howtoeat.domain.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodRequestDto;
import com.daall.howtoeat.admin.food.dto.FoodShareRequestDto;
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
    private String representativeName;

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
    @Column(nullable = true)
    private String providedBy;

    //음식 데이터 출처 : 식약처 / 어드민 / 유저
    @Column(nullable = false)
    private String source;

    //1인분인지 아닌지 판별
    @Column(nullable = false)
    private Boolean isPerServing;

    //음식이 몇번 선택됐는지 -> 추후 인기있는 음식들을 추천 음식에 활용
    @Column
    private Long selectedCount;

    @Version
    private Long version;

    public Food(AdminFoodRequestDto requestDto) {
        this.foodCode = "TEMP";
        this.foodType = requestDto.getFoodType();
        this.foodName = requestDto.getFoodName();
        this.representativeName = requestDto.getRepresentativeName();
        this.kcal = requestDto.getKcal();
        this.carbo = requestDto.getCarbo();
        this.protein = requestDto.getProtein();
        this.fat = requestDto.getFat();
        this.foodWeight = requestDto.getFoodWeight();
        this.unit = requestDto.getUnit();
        this.providedBy = requestDto.getProvidedBy();
        this.source = "Admin";
        this.isPerServing = requestDto.getIsPerServing();
        this.selectedCount = 0L;
    }

    public void setFoodCode(String foodCode){
        this.foodCode = foodCode;
    }

    public void updateFood(AdminFoodRequestDto requestDto) {
        this.foodType = requestDto.getFoodType();
        this.foodName = requestDto.getFoodName();
        this.representativeName = requestDto.getRepresentativeName();
        this.kcal = requestDto.getKcal();
        this.carbo = requestDto.getCarbo();
        this.protein = requestDto.getProtein();
        this.fat = requestDto.getFat();
        this.foodWeight = requestDto.getFoodWeight();
        this.unit = requestDto.getUnit();
        this.providedBy = requestDto.getProvidedBy();
        this.isPerServing = requestDto.getIsPerServing();
    }

    public Food(FoodShareRequestDto requestDto) {
        this.foodCode = requestDto.getFoodCode();
        // 유저 등록 음식으로 FoodType 고정
        this.foodType = FoodType.CUSTOM;
        this.foodName = requestDto.getFoodName();
        this.representativeName = requestDto.getRepresentativeName();
        this.kcal = requestDto.getKcal();
        this.carbo = requestDto.getCarbo();
        this.protein = requestDto.getProtein();
        this.fat = requestDto.getFat();
        this.foodWeight = requestDto.getFoodWeight();
        this.unit = requestDto.getUnit();
        this.providedBy = requestDto.getProvidedBy();
        this.isPerServing = requestDto.getIsPerServing();
        this.source = "User";
    }

    public void addSelectedCount(){
        selectedCount++;
    }
}
