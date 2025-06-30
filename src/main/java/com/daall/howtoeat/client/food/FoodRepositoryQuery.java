package com.daall.howtoeat.client.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;
import com.daall.howtoeat.domain.food.Food;
import org.springframework.data.domain.Page;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface FoodRepositoryQuery {
    Page<AdminFoodResponseDto> searchFoods(int page, int size, String name, String orderBy, String foodType, String recommendation);
    Optional<AdminFoodResponseDto> findFoodAndRecommendationById(Long foodId);
}
