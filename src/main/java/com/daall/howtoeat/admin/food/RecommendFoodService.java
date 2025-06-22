package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.domain.food.Food;
import com.daall.howtoeat.domain.food.RecommendFood;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendFoodService {
    private final RecommendFoodRepository recommendFoodRepository;

    public void createRecommendFood(Food food) {
        Optional<RecommendFood> recommendFood = recommendFoodRepository.findByFood(food);

        // 이미 있다면 throw 하지않고 진행
        if(recommendFood.isPresent()){
            return;
        }

        RecommendFood newRecommendFood = new RecommendFood(food);
        recommendFoodRepository.save(newRecommendFood);
    }
}
