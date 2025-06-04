package com.daall.howtoeat.dummy;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.domain.food.Food;
import com.daall.howtoeat.client.food.FoodRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateDummyDataFood {

    @Autowired
    private FoodRepository foodRepository;


    /**
     * 음식 데이터 12개
     */
    @Test
    @Order(1)
    @Transactional
    @Rollback(value = false)
    void createCookedFoodDummyData() {
        String[] foodCodeList = {
                "Admin407",
                "Admin408",
                "Admin409",
                "Admin410",
                "Admin411",
                "Admin412",

                "D101-007280000-0001",
                "D101-017280000-0001",
                "D101-035280000-0001",
                "D105-219240000-0001",
                "D105-223240000-0001",
                "D105-225240000-0001",
        };

        String[] foodTypeList = {
                "COOKED",
                "COOKED",
                "COOKED",
                "COOKED",
                "COOKED",
                "COOKED",

                "COOKED",
                "COOKED",
                "COOKED",
                "COOKED",
                "COOKED",
                "COOKED",
        };

        String[] foodNameList = {
                "소고기구이",
                "소고기국밥",
                "소고기무국",
                "소고기사태찜",
                "소고기산적",
                "소고기육개장",

                "김밥 소고기",
                "볶음밥 소고기",
                "주먹밥 소고기",
                "무국 소고기",
                "미역국 소고기",
                "배추국 소고기",
        };

        String[] representativeNameList = {
                "소고기",
                "소고기",
                "소고기",
                "소고기",
                "소고기",
                "소고기",

                "소고기",
                "소고기",
                "소고기",
                "소고기",
                "소고기",
                "소고기",
        };

        String[] foodWeightList = {
                "200",
                "600",
                "400",
                "300",
                "200",
                "300",

                "250",
                "260",
                "200",
                "400",
                "400",
                "400",
        };

        String[] foodKcalList = {
                "480",
                "282",
                "120",
                "327",
                "450",
                "147",

                "448",
                "458",
                "356",
                "60",
                "52",
                "64",
        };

        String[] foodCarboList = {
                "0",
                "37",
                "8",
                "11",
                "7",
                "9",

                "64",
                "57",
                "69",
                "4",
                "3",
                "5",
        };

        String[] foodProteinList = {
                "56",
                "19",
                "14",
                "36",
                "48",
                "9",

                "16",
                "20",
                "10",
                "5",
                "7",
                "7",
        };

        String[] foodFatList = {
                "27",
                "7",
                "4",
                "16",
                "26",
                "8",

                "14",
                "17",
                "4",
                "3",
                "1",
                "2",
        };

        String[] unitList = {
                "g",
                "g",
                "g",
                "g",
                "g",
                "g",

                "g",
                "g",
                "g",
                "g",
                "g",
                "g",
        };

        String[] providedByList = {
                "(주) 하잇",
                "(주) 넥스트짐",
                "(주) CJ플러스",
                "-",
                "-",
                "(주) 해태음식",

                "(주) 백송원의맛집",
                "-",
                "-",
                "(주) Banmi",
                "(주) 하나음식",
                "-",
        };

        String[] sourceList = {
                "Admin",
                "Admin",
                "Admin",
                "Admin",
                "Admin",
                "Admin",

                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
        };

        boolean[] isPerServingList = {
                true,
                true,
                true,
                true,
                true,
                true,

                true,
                true,
                true,
                true,
                true,
                true,
        };

        List<Food> foodList = new ArrayList<>();

        for(int i = 0; i < foodCodeList.length; i++) {
            Long id = (long) i + 1L;
            String foodCode = foodCodeList[i];
            String foodType = foodTypeList[i];
            String foodName = foodNameList[i];
            String representativeName = representativeNameList[i];
            String kcal = foodKcalList[i];
            String carbo = foodCarboList[i];
            String protein = foodProteinList[i];
            String fat = foodFatList[i];
            Double foodWeight = Double.valueOf(foodWeightList[i]);
            String unit = unitList[i];
            String providedBy = providedByList[i];
            String source = sourceList[i];
            boolean isPerServing = isPerServingList[i];


            Food food = new Food();
            setField(food, "foodCode", foodCode);
            setField(food, "foodType", FoodType.valueOf(foodType));
            setField(food, "foodName", foodName);
            setField(food, "representative_name", representativeName);
            setField(food, "kcal", Double.valueOf(kcal));
            setField(food, "carbo", Double.valueOf(carbo));
            setField(food, "protein", Double.valueOf(protein));
            setField(food, "fat", Double.valueOf(fat));
            setField(food, "foodWeight", Double.valueOf(foodWeight));
            setField(food, "unit", unit);
            setField(food, "provided_by", providedBy);
            setField(food, "source", source);
            setField(food, "isPerServing", isPerServing);

            foodList.add(food);
        }
        foodRepository.saveAll(foodList);
    }


    /**
     * 가공식품 음식 데이터 12개
     */
    @Test
    @Order(2)
    @Transactional
    @Rollback(value = false)
    void createProcessedFoodDummyData() {
        String[] foodCodeList = {
                "P123-201020300-1814",
                "P123-305020300-0100",
                "P123-210020300-0189",
                "P117-800080000-0079",
                "P123-212020300-1095",
                "P123-210020300-0326",

                "P123-204020300-0612",
                "P123-201020300-1815",
                "P123-212020300-1272",
                "P123-201020300-1810",
                "P123-213020200-0459",
                "P123-305020300-0099",
        };

        String[] foodTypeList = {
                "PROCESSED",
                "PROCESSED",
                "PROCESSED",
                "PROCESSED",
                "PROCESSED",
                "PROCESSED",

                "PROCESSED",
                "PROCESSED",
                "PROCESSED",
                "PROCESSED",
                "PROCESSED",
                "PROCESSED",
        };

        String[] foodNameList = {
                "소고기야채볶음밥",
                "소고기잡채",
                "소고기감자야채조림",
                "교동 소고기장조림",
                "소고기애호박두부볶음",
                "소고기장조림",

                "스트릿 대만식 소고기 우육탕면",
                "소고기야채볶음밥키트",
                "소고기강된장",
                "소고기곤약볶음밥",
                "소고기야채죽돼지고기장조림도시락",
                "소고기유니짜장소스",
        };

        String[] representativeNameList = {
                "밥류",
                "기타 즉석식품",
                "조림/찜",
                "기타 식육가공품",
                "반찬",
                "조림/찜",

                "즉석 면요리",
                "밥류",
                "반찬",
                "밥류",
                "도시락",
                "기타 즉석식품",
        };

        Double[] foodWeightList = {
                120.0,
                100.0,
                100.0,
                180.0,
                150.0,
                250.0,

                370.0,
                110.0,
                100.0,
                400.0,
                700.0,
                350.0,
        };

        Double[] foodKcalList = {
                84.0,
                99.0,
                99.0,
                97.0,
                96.0,
                96.0,

                96.0,
                96.0,
                95.0,
                94.0,
                93.0,
                93.0,
        };

        Double[] foodCarboList = {
                15.00,
                19.00,
                12.00,
                8.69,
                5.00,
                10.00,

                15.68,
                10.00,
                8.59,
                15.00,
                12.07,
                12.86,
        };

        Double[] foodProteinList = {
                5.83,
                4.00,
                4.00,
                12.36,
                6.00,
                7.41,

                4.59,
                4.55,
                6.51,
                3.50,
                3.87,
                1.43,
        };

        Double[] foodFatList = {
                1.92,
                1.20,
                3.90,
                1.26,
                6.00,
                2.93,

                1.62,
                4.55,
                3.72,
                1.95,
                3.03,
                4.00,
        };

        String[] unitList = {
                "g",
                "g",
                "g",
                "g",
                "g",
                "g",

                "g",
                "g",
                "g",
                "g",
                "g",
                "g",
        };

        String[] providedByList = {
                "(주)푸드트리",
                "(주)아이푸드",
                "(주)아이푸드",
                "(주)교동식품제4공장",
                "주식회사 지니온",
                "맛있는찬",

                "(주)우양",
                "(주)현대그린푸드",
                "주식회사동방푸드마스타",
                "디유푸드(주)",
                "(주)비엘푸드",
                "(주)비움",
        };

        String[] sourceList = {
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",

                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
                "식품의약품안전처",
        };

        boolean[] isPerServingList = {
                false,
                false,
                false,
                false,
                false,
                false,

                false,
                false,
                false,
                false,
                false,
                false,
        };

        List<Food> foodList = new ArrayList<>();

        for(int i = 0; i < foodCodeList.length; i++) {
            Long id = (long) i + 13L;
            String foodCode = foodCodeList[i];
            String foodType = foodTypeList[i];
            String foodName = foodNameList[i];
            String representativeName = representativeNameList[i];
            Double kcal = foodKcalList[i];
            Double carbo = foodCarboList[i];
            Double protein = foodProteinList[i];
            Double fat = foodFatList[i];
            Double foodWeight = foodWeightList[i];
            String unit = unitList[i];
            String providedBy = providedByList[i];
            String source = sourceList[i];
            boolean isPerServing = isPerServingList[i];


            Food food = new Food();
            setField(food, "foodCode", foodCode);
            setField(food, "foodType", FoodType.valueOf(foodType));
            setField(food, "foodName", foodName);
            setField(food, "representative_name", representativeName);
            setField(food, "kcal", kcal);
            setField(food, "carbo", carbo);
            setField(food, "protein", protein);
            setField(food, "fat", fat);
            setField(food, "foodWeight", foodWeight);
            setField(food, "unit", unit);
            setField(food, "provided_by", providedBy);
            setField(food, "source", source);
            setField(food, "isPerServing", isPerServing);

            foodList.add(food);
            foodRepository.saveAll(foodList);
        }
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 원재료 음식 데이터 12개
     */
    @Test
    @Order(3)
    @Transactional
    @Rollback(value = false)
    void createIngredientFoodDummyData() {
        String[] foodCodeList = {
                "R209-027039101-0000",
                "R209-027031801-0000",
                "R209-027031846-0000",
                "R209-027038346-0000",
                "R209-027038351-0000",
                "R209-027038101-0000",

                "R209-027018750-0000",
                "R209-027011352-0000",
                "R209-027018548-0000",
                "R209-027018401-0000",
                "R209-027018446-0000",
                "R209-027018153-0000",
        };

        String[] foodTypeList = {
                "INGREDIENT",
                "INGREDIENT",
                "INGREDIENT",
                "INGREDIENT",
                "INGREDIENT",
                "INGREDIENT",

                "INGREDIENT",
                "INGREDIENT",
                "INGREDIENT",
                "INGREDIENT",
                "INGREDIENT",
                "INGREDIENT",
        };

        String[] foodNameList = {
                "소고기 한우 채끝(채끝살) 생것",
                "소고기 한우 양지 생것",
                "소고기 한우 양지 삶은것",
                "소고기 한우 등심 삶은것",
                "소고기 한우 등심 구운것(팬)",
                "소고기 한우 갈비 생것",

                "소고기 수입산(미국산) 안심(안심살) 구운것",
                "소고기 수입산(미국산) 설도 구운것(석쇠)",
                "소고기 수입산(미국산) 사태 끓인것",
                "소고기 수입산(미국산) 목심(목심살) 생것",
                "소고기 수입산(미국산) 목심(목심살) 삶은것",
                "소고기 수입산(미국산) 갈비 구운것(오븐)",
        };

        String[] representativeNameList = {
                "소고기",
                "소고기",
                "소고기",
                "소고기",
                "소고기",
                "소고기",

                "소고기",
                "소고기",
                "소고기",
                "소고기",
                "소고기",
                "소고기",
        };

        Double[] foodWeightList = {
                100.0,
                100.0,
                100.0,
                100.0,
                100.0,
                100.0,

                100.0,
                100.0,
                100.0,
                100.0,
                100.0,
                100.0,
        };

        Double[] foodKcalList = {
                126.0,
                253.0,
                272.0,
                357.0,
                400.0,
                307.0,

                267.0,
                164.0,
                201.0,
                268.0,
                359.0,
                400.0,
        };

        Double[] foodCarboList = {
                0.2,
                0.0,
                0.0,
                0.0,
                0.0,
                1.9,

                0.0,
                0.0,
                0.0,
                0.97,
                0.0,
                0.0,
        };

        Double[] foodProteinList = {
                17.1,
                18.58,
                33.09,
                25.2,
                18.9,
                16.5,

                26.46,
                28.45,
                33.68,
                16.98,
                26.37,
                22.57,
        };

        Double[] foodFatList = {
                5.6,
                18.59,
                14.05,
                26.63,
                33.99,
                24.4,

                17.12,
                4.67,
                6.36,
                21.31,
                27.26,
                33.7,
        };

        String[] unitList = {
                "g",
                "g",
                "g",
                "g",
                "g",
                "g",

                "g",
                "g",
                "g",
                "g",
                "g",
                "g",
        };

        String[] providedByList = {
                "-",
                "-",
                "-",
                "-",
                "-",
                "-",

                "-",
                "-",
                "-",
                "-",
                "-",
                "-",
        };

        String[] sourceList = {
                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",

                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",
                "농촌진흥청(국가표준식품성분표)",
        };

        boolean[] isPerServingList = {
                false,
                false,
                false,
                false,
                false,
                false,

                false,
                false,
                false,
                false,
                false,
                false,
        };

        List<Food> foodList = new ArrayList<>();

        for(int i = 0; i < foodCodeList.length; i++) {
            Long id = (long) i + 26L;
            String foodCode = foodCodeList[i];
            String foodType = foodTypeList[i];
            String foodName = foodNameList[i];
            String representativeName = representativeNameList[i];
            Double kcal = foodKcalList[i];
            Double carbo = foodCarboList[i];
            Double protein = foodProteinList[i];
            Double fat = foodFatList[i];
            Double foodWeight = foodWeightList[i];
            String unit = unitList[i];
            String providedBy = providedByList[i];
            String source = sourceList[i];
            boolean isPerServing = isPerServingList[i];


            Food food = new Food();
            setField(food, "foodCode", foodCode);
            setField(food, "foodType", FoodType.valueOf(foodType));
            setField(food, "foodName", foodName);
            setField(food, "representative_name", representativeName);
            setField(food, "kcal", kcal);
            setField(food, "carbo", carbo);
            setField(food, "protein", protein);
            setField(food, "fat", fat);
            setField(food, "foodWeight", foodWeight);
            setField(food, "unit", unit);
            setField(food, "provided_by", providedBy);
            setField(food, "source", source);
            setField(food, "isPerServing", isPerServing);

            foodList.add(food);
            foodRepository.saveAll(foodList);
        }
    }

}
