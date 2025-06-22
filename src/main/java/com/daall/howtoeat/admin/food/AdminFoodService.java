package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.admin.food.dto.AdminFoodResponseDto;
import com.daall.howtoeat.admin.food.dto.AdminFoodRequestDto;
import com.daall.howtoeat.client.food.FoodRepository;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.food.Food;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFoodService {
    private final FoodRepository foodRepository;
    private final RecommendFoodService recommendFoodService;

    public Page<AdminFoodResponseDto> getFoods(int page, int size,String name, String orderBy, String foodType, String recommendation) {
        return foodRepository.searchFoods(page, size, name, orderBy, foodType, recommendation);
    }

    public AdminFoodResponseDto getFood(Long foodId) {
        return foodRepository.findFoodAndRecommendationById(foodId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_FOOD)
        );
    }

    @Transactional
    public void createFood(AdminFoodRequestDto requestDto) {
        Food newFood = new Food(requestDto);
        foodRepository.save(newFood);

        Integer codeNumber = foodRepository.findMaxAdminFoodCodeNumber();
        newFood.setFoodCode("Admin" + (codeNumber + 1));

        // 추천음식이면
        if(requestDto.getIsRecommended()){
            recommendFoodService.createRecommendFood(newFood);
        }
    }

    @Transactional
    public void updateFood(Long foodId, AdminFoodRequestDto requestDto) {
        Food food = this.findById(foodId);

        food.updateFood(requestDto);

        // 추천음식 처리
        if(requestDto.getIsRecommended()){
            recommendFoodService.createRecommendFood(food);
        } else {
            recommendFoodService.deleteRecommendFood(food);
        }
    }

    @Transactional
    public void deleteFood(Long foodId) {
        Food food = this.findById(foodId);

        //추천음식 처리
        recommendFoodService.deleteRecommendFood(food);
        foodRepository.delete(food);
    }

    public Food findById(Long foodId){
        return foodRepository.findById(foodId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_FOOD)
        );
    }
}
