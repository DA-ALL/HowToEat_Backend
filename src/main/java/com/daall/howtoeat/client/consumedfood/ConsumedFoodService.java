package com.daall.howtoeat.client.consumedfood;

import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodByMealTimeResponseDto;
import com.daall.howtoeat.client.consumedfood.dto.ConsumedFoodsRequestDto;
import com.daall.howtoeat.client.food.dto.FoodResponseDto;
import com.daall.howtoeat.client.user.UserTargetService;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryService;
import com.daall.howtoeat.client.userdailysummary.dto.DailyNutritionSummary;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import com.daall.howtoeat.domain.user.UserTarget;
import jakarta.transaction.Transactional;
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
    private final UserDailySummaryService userDailySummaryService;
    private final UserTargetService userTargetService;

    /**
     * 섭취 음식 조회
     * @param loginUser 유저 정보
     * @param localDate 음식 섭취 날짜
     * @param mealTime 음식 섭취 끼니(아침 점심 저녁 간식)
     */
    public List<ConsumedFoodByMealTimeResponseDto> getConsumedFoodList(User loginUser, LocalDate localDate, MealTime mealTime) {
        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime end = localDate.atTime(23, 59, 59);

        List<ConsumedFood> consumedFoods = consumedFoodRepository.findAllByUserAndCreatedAtBetweenAndMealTime(loginUser, start, end, mealTime);

        List<ConsumedFoodByMealTimeResponseDto> responseDtos = new ArrayList<>();

        for (ConsumedFood consumedFood : consumedFoods) {
            responseDtos.add(new ConsumedFoodByMealTimeResponseDto(consumedFood));
        }

        return responseDtos;
    }

    /**
     * 섭취 음식 등록
     * @param loginUser 현재 로그인한 유저
     * @param requestDtoList 등록할 음식 정보 리스트 (다중 등록 가능)
     */
    @Transactional
    public void addConsumedFoods(User loginUser, List<ConsumedFoodsRequestDto> requestDtoList) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        // 1. 전달받은 음식 리스트를 엔티티로 변환 후 저장
        List<ConsumedFood> consumedFoods = new ArrayList<>();
        for (ConsumedFoodsRequestDto requestDto : requestDtoList) {
            consumedFoods.add(new ConsumedFood(loginUser, requestDto));
        }
        consumedFoodRepository.saveAll(consumedFoods);

        // 2. 오늘 날짜 기준 유저의 요약 정보 조회 (존재하지 않을 수 있음)
        UserDailySummary summary = userDailySummaryService.findUserDailySummary(loginUser, today).orElse(null);

        // 3. 오늘 날짜에 기록된 전체 섭취 음식 조회 및 영양소 요약 계산
        List<ConsumedFood> totalConsumedFoods = consumedFoodRepository.findAllByUserAndCreatedAtBetween(loginUser, startOfDay, endOfDay).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_CONSUMED_FOOD));
        DailyNutritionSummary nutritionSummary = new DailyNutritionSummary(totalConsumedFoods);

        // 4. 오늘 날짜에 적용할 최신 목표 정보 조회
        UserTarget latestTarget = userTargetService.getLatestTargetBeforeOrOn(loginUser, today);

        // 5. 오늘 요약 정보가 없으면 새로 생성, 있으면 갱신
        if (summary == null) {
            userDailySummaryService.createUserDailySummary(loginUser, latestTarget, nutritionSummary);
        } else {
            summary.setData(loginUser, latestTarget, nutritionSummary);
        }
    }
}
