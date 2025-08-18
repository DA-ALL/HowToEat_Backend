package com.daall.howtoeat.client.food.elastic;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor
@Document(indexName = "howtoeat-foods")
public class FoodDocument {
    @Id
    private Long id;  // DB와 동일하게 id를 사용

    private String foodCode;

    private String foodType;  // enum 대신 String 저장

    private String foodName;

    private String representativeName;

    private Double kcal;

    private Double carbo;

    private Double protein;

    private Double fat;

    private Double foodWeight;

    private String unit;

    private String providedBy;

    private String source;

    private Boolean isPerServing;

    private Long selectedCount;

    // 생성자 추가: Entity -> Document 변환용
    public FoodDocument(com.daall.howtoeat.domain.food.Food food) {
        this.id = food.getId();
        this.foodCode = food.getFoodCode();
        this.foodType = food.getFoodType().name();
        this.foodName = food.getFoodName();
        this.representativeName = food.getRepresentativeName();
        this.kcal = food.getKcal();
        this.carbo = food.getCarbo();
        this.protein = food.getProtein();
        this.fat = food.getFat();
        this.foodWeight = food.getFoodWeight();
        this.unit = food.getUnit();
        this.providedBy = food.getProvidedBy();
        this.source = food.getSource();
        this.isPerServing = food.getIsPerServing();
        this.selectedCount = food.getSelectedCount();
    }
}
