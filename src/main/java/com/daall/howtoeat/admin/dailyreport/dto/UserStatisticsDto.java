package com.daall.howtoeat.admin.dailyreport.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserStatisticsDto {
    private final Long totalUserCount;
    private final Long todayRegisteredUserCount;
    private final Long maleUserCount;
    private final Long femaleUserCount;
    private final Long nextGymMemberCount;
}
