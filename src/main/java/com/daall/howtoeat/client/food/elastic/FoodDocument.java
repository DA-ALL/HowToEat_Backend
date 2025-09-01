package com.daall.howtoeat.client.food.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.*;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Setting(settingPath = "/elasticsearch/settings.json")
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
                    @InnerField(suffix = "keyword", type = FieldType.Keyword, normalizer = "lower_ascii")
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
            mainField = @Field(type = FieldType.Keyword , normalizer = "lower_ascii"),
            otherFields = {
                    @InnerField(suffix = "text", type = FieldType.Text, analyzer = "nori"),
            }
    )
    private String providedBy;

    @Field(type = FieldType.Keyword)
    private String source;

    @Field(type = FieldType.Boolean)
    private Boolean isPerServing;

    @Field(type = FieldType.Long)
    private Long selectedCount;

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
    }

}
