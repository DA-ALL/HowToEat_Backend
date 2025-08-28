package com.daall.howtoeat.admin.dailyreport;

import com.daall.howtoeat.admin.dailyreport.dto.*;
import com.daall.howtoeat.admin.consumedfood.AdminConsumedFoodService;
import com.daall.howtoeat.admin.food.AdminFoodService;
import com.daall.howtoeat.admin.user.AdminUserService;
import com.daall.howtoeat.domain.dailyreport.DailyReport;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyReportService {
    private final DailyReportRepository dailyReportRepository;
    private final AdminUserService adminUserService;
    private final AdminFoodService adminFoodService;
    private final AdminConsumedFoodService adminConsumedFoodService;

    public DailyReportResponseDto getDailyReport() {
        Optional<DailyReport> dailyReport = dailyReportRepository.findTopByOrderByCreatedAtDesc();

        return dailyReport.map(DailyReportResponseDto::new).orElse(null);
    }

    @Transactional
    public void updateReport() {
        LocalDate today = LocalDate.now();

        Optional<DailyReport> optionalReport = dailyReportRepository.findTopByOrderByCreatedAtDesc();
        DailyReport report;

        if (optionalReport.isPresent()) {
            LocalDate reportDate = optionalReport.get().getCreatedAt().toLocalDate();

            if (reportDate.isEqual(today)) {
                // 오늘 날짜면 업데이트용 객체로 사용
                report = optionalReport.get();
            } else {
                // 오늘 날짜가 아니면 새로 생성
                report = new DailyReport(0);
            }
        } else {
            // 아예 없는 경우 새로 생성
            report = new DailyReport(0);
        }

        /**
         * 전체 유저 수
         * 오늘 회원가입한 유저 수
         * 남자 유저 수
         * 여자 유저 수
         * 넥스트짐 유저 수
         *
         * 전체 음식 수(어드민 + 유저등록음식)
         * 전체 음식(어드민) 수
         * 전체 유저등록 음식 수
         *
         * 오늘 등록된 섭취 음식 수
         */
        UserStatisticsDto userStatisticsDto = adminUserService.getUserStatistics(today);
        FoodStatisticsDto foodStatisticsDto = adminFoodService.getFoodStatistics();
        ConsumedFoodStatisticsDto consumedFoodStatisticsDto = adminConsumedFoodService.getConsumedFoodStatistics(today);

        report.update(userStatisticsDto, foodStatisticsDto, consumedFoodStatisticsDto);

        dailyReportRepository.save(report); // 새 객체일 수도 있고, 기존 객체일 수도 있음
    }

    public List<DailyConsumedFoodCountResponseDto> getRecent30DaysTodayConsumedFoodCounts() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(29);

        return adminConsumedFoodService.getConsumedFoodCountsBetween(startDate, endDate);
    }
}
