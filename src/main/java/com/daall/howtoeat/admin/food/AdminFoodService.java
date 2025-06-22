package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;
import com.daall.howtoeat.client.food.FoodRepository;
import com.daall.howtoeat.domain.food.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFoodService {
    private final FoodRepository foodRepository;

    public Page<AdminFoodResponseDto> getFoods(int page, int size,String name, String orderBy, String foodType, String recommendation) {
        return foodRepository.searchFoods(page, size, name, orderBy, foodType, recommendation);
    }
}
