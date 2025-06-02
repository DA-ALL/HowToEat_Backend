package com.daall.howtoeat.client.consumedfood;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodByMealTimeResponseDto;
import com.daall.howtoeat.common.enums.MealTime;
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

}
