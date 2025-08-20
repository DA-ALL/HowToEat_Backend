package com.daall.howtoeat.client.food.elastic;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.Instant;
import java.time.ZoneId;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "howtoeat-foods")
public class FoodDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;  // DB와 동일하게 id를 사용

    @Field(type = FieldType.Keyword)
    private String foodCode;

    @Field(type = FieldType.Keyword)
    private String foodType;  // enum 대신 String 저장

    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "nori"),
            otherFields = {
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String foodName;

    @Field(type = FieldType.Keyword)
    private String representativeName;

    @Field(type = FieldType.Double)
    private Double kcal;

    @Field(type = FieldType.Double)
    private Double carbo;

    @Field(type = FieldType.Double)
    private Double protein;

    @Field(type = FieldType.Double)
    private Double fat;

    @Field(type = FieldType.Double)
    private Double foodWeight;

    @Field(type = FieldType.Keyword)
    private String unit;

    @MultiField(
            mainField = @Field(type = FieldType.Keyword /*, normalizer = "lower_ascii"*/),
            otherFields = {
                    @InnerField(suffix = "text", type = FieldType.Text, analyzer = "nori"),
//                    @InnerField(suffix = "auto", type = FieldType.Text, analyzer = "ko_autocomplete", searchAnalyzer = "ko_search")
            }
    )
    private String providedBy;

    @Field(type = FieldType.Keyword)
    private String source;

    @Field(type = FieldType.Boolean)
    private Boolean isPerServing;

    @Field(type = FieldType.Long)
    private Long selectedCount;

    @Field(type = FieldType.Date, format = DateFormat.epoch_millis) // 실제 저장형식에 맞추세요
    private Instant createdAt;


    // 생성자 추가: Entity -> Document 변환용
    public FoodDocument(com.daall.howtoeat.domain.food.Food food) {
        this.id = String.valueOf(food.getId());
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
//        this.createdAt = food.getCreatedAt();
        if (food.getCreatedAt() != null) {
            this.createdAt = food.getCreatedAt()
                    .atZone(ZoneId.of("Asia/Seoul")) // DB 기준에 맞게. 서버가 UTC면 ZoneOffset.UTC
                    .toInstant();
        }
    }

}
