package com.daall.howtoeat.admin.dailyreport.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ConsumedFoodStatisticsDto {
    private final Long totalConsumedFoodCount;
    private final Long todayConsumedFoodCount;
}
