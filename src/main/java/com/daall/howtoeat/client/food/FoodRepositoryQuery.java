package com.daall.howtoeat.client.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;
import com.daall.howtoeat.domain.food.Food;
import org.springframework.data.domain.Page;

public interface FoodRepositoryQuery {
    Page<AdminFoodResponseDto> searchFoods(int page, int size, String name, String orderBy, String foodType, String recommendation);
}
