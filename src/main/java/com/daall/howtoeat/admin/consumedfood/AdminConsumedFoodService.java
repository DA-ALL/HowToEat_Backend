package com.daall.howtoeat.admin.consumedfood;

import com.daall.howtoeat.admin.consumedfood.dto.ConsumedFoodsByDateResponseDto;
import com.daall.howtoeat.admin.dailyreport.dto.ConsumedFoodStatisticsDto;
import com.daall.howtoeat.client.consumedfood.ConsumedFoodRepository;
import com.daall.howtoeat.client.user.UserService;
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



    public List<List<ConsumedFoodsByDateResponseDto>> getConsumedFoodsByDate(Long userId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);
        String[] mealTimeList = {"BREAKFAST", "LUNCH", "DINNER", "SNACK"};

        List<ArrayList<ConsumedFoodsByDateResponseDto>> consumedFoodsList = new ArrayList<>();

        for(int i = 0; i < 4; i++) {
            consumedFoodsList.add(new ArrayList<>());
        }

        User user = userService.getUser(userId);

        for(String mealTime : mealTimeList) {
            List<ConsumedFood> consumedFoods = consumedFoodRepository.findAllByUserAndCreatedAtBetweenAndMealTime(user, start, end, mealTime);

            for(ConsumedFood consumedfood : consumedFoods) {
                new ConsumedFoodsByDateResponseDto(consumedfood);
            }
        }

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
}
