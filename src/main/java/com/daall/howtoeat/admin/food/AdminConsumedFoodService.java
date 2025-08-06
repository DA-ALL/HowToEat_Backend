package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.admin.dailyreport.dto.ConsumedFoodStatisticsDto;
import com.daall.howtoeat.client.consumedfood.ConsumedFoodRepository;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminConsumedFoodService {
    private final ConsumedFoodRepository consumedFoodRepository;

    public ConsumedFoodStatisticsDto getConsumedFoodStatistics(LocalDate today) {
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return new ConsumedFoodStatisticsDto(
                consumedFoodRepository.count(),
                consumedFoodRepository.countByCreatedAtBetween(startOfDay, endOfDay)
        );
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
