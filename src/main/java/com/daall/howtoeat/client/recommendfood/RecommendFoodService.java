package com.daall.howtoeat.client.recommendfood;

import com.daall.howtoeat.admin.food.RecommendFoodRepository;
import com.daall.howtoeat.client.recommendfood.dto.RecommendFoodResponseDto;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryService;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.food.Food;
import com.daall.howtoeat.domain.food.RecommendFood;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendFoodService {
    private final RecommendFoodRepository recommendFoodRepository;
    private final UserDailySummaryService userDailySummaryService;

    public List<RecommendFoodResponseDto> getRecommendFoods(User loginUser, LocalDate date) {
        // 해당 날짜, 해당 유저의 데일리 서머리 조회
        UserDailySummary userDailySummary = userDailySummaryService.findUserDailySummary(loginUser, date).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER_DAILY_SUMMARY)
        );

        UserTarget userTarget = userDailySummary.getUserTarget();

        List<RecommendFood> recommendFoods = recommendFoodRepository.findAllWithFood();

        double remainKcal = Math.max(userTarget.getKcal() - userDailySummary.getTotalKcal(), 0);
        double remainCarbo = Math.max(userTarget.getCarbo() - userDailySummary.getTotalCarbo(), 0);
        double remainProtein = Math.max(userTarget.getProtein() - userDailySummary.getTotalProtein(), 0);
        double remainFat = Math.max(userTarget.getFat() - userDailySummary.getTotalFat(), 0);

        double remainCarboKcal = remainCarbo * 4;
        double remainProteinKcal = remainProtein * 4;
        double remainFatKcal = remainFat * 9;
        // 점수 매기기
        List<ScoredRecommendFood> scoredFoods = recommendFoods.stream()
                .map(rf -> {
                    Food food = rf.getFood();
                    double score = calculateScore(food, remainKcal, remainCarboKcal, remainProteinKcal, remainFatKcal);
                    return new ScoredRecommendFood(rf, score);
                })
                .sorted(Comparator.comparingDouble(ScoredRecommendFood::getScore).reversed()) // 점수 높은 순
                .limit(10) // Top 10만
                .toList();

        return scoredFoods.stream()
                .map(scored -> new RecommendFoodResponseDto(scored.getRecommendFood().getFood()))
                .collect(Collectors.toList());
    }


    private double calculateScore(Food food, double remainKcal, double remainCarboKcal, double remainProteinKcal, double remainFatKcal) {
        double totalGap = remainKcal + remainCarboKcal + remainProteinKcal + remainFatKcal + 1;

        double wKcal = remainKcal / totalGap;
        double wCarboKcal = remainCarboKcal / totalGap;
        double wProteinKcal = remainProteinKcal / totalGap;
        double wFatKcal = remainFatKcal / totalGap;

        double kcalScore = 1 - Math.abs(food.getKcal() - remainKcal) / (remainKcal + 1);
        double carboScore = 1 - Math.abs((food.getCarbo() * 4) - remainCarboKcal) / (remainCarboKcal + 1);
        double proteinScore = 1 - Math.abs((food.getProtein() * 4) - remainProteinKcal) / (remainProteinKcal + 1);
        double fatScore = 1 - Math.abs((food.getFat() * 9) - remainFatKcal) / (remainFatKcal + 1);

        return wKcal * Math.max(0, kcalScore) +
                wCarboKcal * Math.max(0, carboScore) +
                wProteinKcal * Math.max(0, proteinScore) +
                wFatKcal * Math.max(0, fatScore);
    }


    @Getter
    private static class ScoredRecommendFood {
        private final RecommendFood recommendFood;
        private final double score;
        public ScoredRecommendFood(RecommendFood recommendFood, double score) {
            this.recommendFood = recommendFood;
            this.score = score;
        }
    }

}
