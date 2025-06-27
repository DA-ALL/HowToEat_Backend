package com.daall.howtoeat.admin.dailyreport;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyReportScheduler {
    private final DailyReportService dailyReportService;

    @Scheduled(cron = "0 0 * * * *") // 매시 정각
    public void updateDailyReport() {
        dailyReportService.updateReport();
    }
}
