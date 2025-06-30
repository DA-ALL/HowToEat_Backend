package com.daall.howtoeat.admin.dailyreport.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FoodStatisticsDto {
    private final Long totalFoodCount;
    private final Long totalAdminFoodCount;
    private final Long totalUserFoodCount;
}
