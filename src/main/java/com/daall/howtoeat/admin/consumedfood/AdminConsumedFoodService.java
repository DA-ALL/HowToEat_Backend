package com.daall.howtoeat.admin.consumedfood;

import com.daall.howtoeat.admin.consumedfood.dto.ConsumedFoodResponseDto;
import com.daall.howtoeat.admin.consumedfood.dto.ConsumedFoodsByDateResponseDto;
import com.daall.howtoeat.admin.dailyreport.dto.ConsumedFoodStatisticsDto;
import com.daall.howtoeat.admin.dailyreport.dto.DailyConsumedFoodCountResponseDto;
import com.daall.howtoeat.client.consumedfood.ConsumedFoodRepository;
import com.daall.howtoeat.client.user.UserService;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminConsumedFoodService {
    private final ConsumedFoodRepository consumedFoodRepository;
    private final UserService userService;

    public ConsumedFoodStatisticsDto getConsumedFoodStatistics(LocalDate today) {
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return new ConsumedFoodStatisticsDto(
                consumedFoodRepository.count(),
                consumedFoodRepository.countByCreatedAtBetween(startOfDay, endOfDay)
        );
    }

    /**
     * 날짜별 섭취음식 끼니로 구분하여 조회 - 어드민 - 유저섭취정보 팝업
     *
     * @param userId 조회할 유저 아이디
     * @param date 조회할 날짜
     */
    public ConsumedFoodsByDateResponseDto getConsumedFoodsByDate(Long userId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        User user = userService.getUser(userId);

        List<ConsumedFoodResponseDto> breakfastConsumedFoods = new ArrayList<>();
        List<ConsumedFoodResponseDto> lunchConsumedFoods = new ArrayList<>();
        List<ConsumedFoodResponseDto> dinnerConsumedFoods = new ArrayList<>();
        List<ConsumedFoodResponseDto> snackConsumedFoods = new ArrayList<>();

        for(MealTime mealTime : MealTime.values()) {
            List<ConsumedFood> consumedFoods = consumedFoodRepository.findAllByUserAndRegisteredAtAndMealTime(user, date, mealTime);

            for(ConsumedFood consumedFood : consumedFoods) {
                if(mealTime.equals(MealTime.BREAKFAST)) {
                    breakfastConsumedFoods.add(new ConsumedFoodResponseDto(consumedFood));
                }
                if(mealTime.equals(MealTime.LUNCH)) {
                    lunchConsumedFoods.add(new ConsumedFoodResponseDto(consumedFood));
                }
                if(mealTime.equals(MealTime.DINNER)) {
                    dinnerConsumedFoods.add(new ConsumedFoodResponseDto(consumedFood));
                }
                if(mealTime.equals(MealTime.SNACK)) {
                    snackConsumedFoods.add(new ConsumedFoodResponseDto(consumedFood));
                }
            }
        }
        return new ConsumedFoodsByDateResponseDto(breakfastConsumedFoods, lunchConsumedFoods, dinnerConsumedFoods, snackConsumedFoods);
    }

    public Map<Long, Long> getConsumedFoodCountOfUsers(List<Long> userIds){
        try {
            List<Object[]> result = consumedFoodRepository.countConsumedFoodsByUserIds(userIds);
            Map<Long, Long> userIdToFoodCount = new HashMap<>();
            for (Object[] row : result) {
                Long userId = (Long) row[0];
                Long count = (Long) row[1];
                userIdToFoodCount.put(userId, count);
            }


            return userIdToFoodCount;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public Long getConsumedFoodCountOfUser(User user){
        return consumedFoodRepository.countByUser(user);
    }

    public List<DailyConsumedFoodCountResponseDto> getConsumedFoodCountsBetween(LocalDate startDate, LocalDate endDate) {
        List<DailyConsumedFoodCountResponseDto> dailyCounts = consumedFoodRepository.getDailyCounts(startDate, endDate);

        // 2. 빠른 조회를 위해 Map으로 변환
        Map<LocalDate, Long> countMap = dailyCounts.stream()
                .collect(Collectors.toMap(DailyConsumedFoodCountResponseDto::getDate,
                        DailyConsumedFoodCountResponseDto::getConsumedFoodCount));

        // 3. startDate ~ endDate 까지 하루씩 돌면서 결과 채우기
        List<DailyConsumedFoodCountResponseDto> filledCounts = new ArrayList<>();
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            Long count = countMap.getOrDefault(current, 0L);
            filledCounts.add(new DailyConsumedFoodCountResponseDto(current, count));
            current = current.plusDays(1);
        }

        return filledCounts;
    }
}
