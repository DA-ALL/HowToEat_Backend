package com.daall.howtoeat.client.consumedfood;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodByMealTimeResponseDto;
import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodsRequestDto;
import com.daall.howtoeat.client.food.dto.FoodResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumedFoodService {
    private final ConsumedFoodRepository consumedFoodRepository;

    public List<ConsumedFoodByMealTimeResponseDto> getConsumedFoodList(User user, LocalDate localDate, MealTime mealTime) {
        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.atTime(23, 59, 59);


        List<ConsumedFood> consumedFoods = consumedFoodRepository.findAllByUserAndCreatedAtBetweenAndMealTime(user, start, end, mealTime);
        for (ConsumedFood consumedFood : consumedFoods) {
            System.out.println(consumedFood.getFoodName());
            System.out.println(consumedFood.getFoodCode());
            System.out.println("=======================");

        }

        List<ConsumedFoodByMealTimeResponseDto> responseDtos = new ArrayList<>();

        for (ConsumedFood consumedFood : consumedFoods) {
            responseDtos.add(new ConsumedFoodByMealTimeResponseDto(consumedFood.getId(), consumedFood.getFoodName(), consumedFood.getKcal(), consumedFood.getFoodWeight(), consumedFood.getUnit()));
        }

        return responseDtos;
    }

    /**
     * 섭취 음식 등록
     * @param loginUser 유저 정보
     * @param requestDtoList 등록할 음식 정보 리스트 -> 다중 등록 가능
     */
    public void addConsumedFoods(User loginUser, List<ConsumedFoodsRequestDto> requestDtoList) {
        List<ConsumedFood> consumedFoods = new ArrayList<>();

        for (ConsumedFoodsRequestDto requestDto : requestDtoList) {
            consumedFoods.add(new ConsumedFood(requestDto, loginUser));
        }

        consumedFoodRepository.saveAll(consumedFoods);
    }
}
