package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;

import java.util.List;

public interface RecommendFoodRepositoryQuery {
    List<AdminFoodResponseDto> searchRecommendFoodsAndFoods(String sortBy);
}
