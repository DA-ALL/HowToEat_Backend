package com.daall.howtoeat.admin.dailyreport;

import com.daall.howtoeat.admin.dailyreport.dto.DailyConsumedFoodCountResponseDto;
import com.daall.howtoeat.admin.dailyreport.dto.DailyReportResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DailyReportController {
    private final DailyReportService dailyReportService;

    @GetMapping("/admin/daily-reports/today")
    public ResponseEntity<ResponseDataDto<DailyReportResponseDto>> getDailyReport(){
        DailyReportResponseDto responseDto = dailyReportService.getDailyReport();
        SuccessType successType = SuccessType.GET_DAILY_REPORT_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }

    @GetMapping("/admin/daily-reports/consumed-food-count/recent-30-days")
    public ResponseEntity<ResponseDataDto<List<DailyConsumedFoodCountResponseDto>>> getRecent30DaysTodayConsumedFoodCounts(){
        List<DailyConsumedFoodCountResponseDto> responseDto = dailyReportService.getRecent30DaysTodayConsumedFoodCounts();
        SuccessType successType = SuccessType.GET_DAILY_REPORT_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }
}
