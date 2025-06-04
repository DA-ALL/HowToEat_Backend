package com.daall.howtoeat.dummy;

import com.daall.howtoeat.common.enums.FoodType;
import com.daall.howtoeat.domain.food.Food;
import com.daall.howtoeat.domain.food.FoodRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateDummyDataFood {

    @Autowired
    private FoodRepository foodRepository;

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

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
