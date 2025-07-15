package com.daall.howtoeat.admin.dailyreport.dto;

import com.daall.howtoeat.domain.dailyreport.DailyReport;
import lombok.Getter;

@Getter
public class DailyReportResponseDto {
    private final Long id;
    private final Long totalUserCount;
    private final Long todayRegisteredUserCount;
    private final Long maleUserCount;
    private final Long femaleUserCount;
    private final Long nextGymMemberCount;
    private final Long totalFoodCount;
    private final Long totalConsumedFoodCount;
    private final Long todayConsumedFoodCount;
    private final Long totalAdminFoodCount;
    private final Long totalUserFoodCount;

    public DailyReportResponseDto(DailyReport dailyReport) {
        this.id = dailyReport.getId();
        this.totalUserCount = dailyReport.getTotalUserCount();
        this.todayRegisteredUserCount = dailyReport.getTodayRegisteredUserCount();
        this.maleUserCount = dailyReport.getMaleUserCount();
        this.femaleUserCount = dailyReport.getFemaleUserCount();
        this.nextGymMemberCount = dailyReport.getNextGymMemberCount();
        this.totalFoodCount = dailyReport.getTotalFoodCount();
        this.totalConsumedFoodCount = dailyReport.getTotalConsumedFoodCount();
        this.todayConsumedFoodCount = dailyReport.getTodayConsumedFoodCount();
        this.totalAdminFoodCount = dailyReport.getTotalAdminFoodCount();
        this.totalUserFoodCount = dailyReport.getTotalUserFoodCount();
    }
}
