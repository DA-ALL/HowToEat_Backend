package com.daall.howtoeat.admin.food;

import com.daall.howtoeat.admin.dailyreport.dto.ConsumedFoodStatisticsDto;
import com.daall.howtoeat.client.consumedfood.ConsumedFoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
}
