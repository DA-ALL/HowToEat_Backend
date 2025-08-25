package com.daall.howtoeat.admin.userdailysummary;

import com.daall.howtoeat.admin.userdailysummary.dto.DailyCaloriesSummaryDto;
import com.daall.howtoeat.admin.userdailysummary.dto.DailyMacrosWithDatesResponseDto;
import com.daall.howtoeat.admin.userdailysummary.dto.DailySummariesByMealTimeResponseDto;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryService;
import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosResponseDto;
import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class AdminUserDailySummaryController {
    private final AdminUserDailySummaryService adminUserDailySummaryService;
    private final UserDailySummaryService userDailySummaryService;

    @GetMapping("/admin/users/{userId}/daily-summaries/calories")
    public ResponseEntity<PageResponseDto<DailyCaloriesSummaryDto>> getUserDailyCalories(
            @PathVariable Long userId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size
    ){
        Page<DailyCaloriesSummaryDto> responseDto = adminUserDailySummaryService.getUserDailyCalories(userId, page-1, size);
        SuccessType successType = SuccessType.GET_DAILY_KCAL_SUMMARIES_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new PageResponseDto<>(successType, responseDto));
    }


    /**
     * 유저 dailySummary 정보 + 이전 or 다음 날짜 조회
     *
     * @PathVariable userId 조회할 유저 아이디
     * @PathVariable date 조회할 날짜
     * @return ResponseEntity<ResponseDataDto<DailyMacrosWithDatesResponseDto>>
     *
     */
    @GetMapping("/admin/users/{userId}/daily-summaries/{date}/macros")
    public ResponseEntity<ResponseDataDto<DailyMacrosWithDatesResponseDto>> getUserDailySummaryMacros(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        DailyMacrosWithDatesResponseDto dto = adminUserDailySummaryService.getUserDailySummaryMacrosWithDates(userId, date);
        SuccessType successType = SuccessType.GET_DAILY_MACROS_SUMMARIES_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, dto));
    }


    /**
     * 유저 dailySummary 정보 + 이전 or 다음 날짜 조회
     *
     * @PathVariable userId 조회할 유저 아이디
     * @PathVariable date 조회할 날짜
     * @return ResponseEntity<ResponseDataDto<DailyMacrosWithDatesResponseDto>>
     *
     */
    @GetMapping("/admin/users/{userId}/daily-summaries/{date}/meal-times")
    public ResponseEntity<ResponseDataDto<DailySummariesByMealTimeResponseDto>> getUserDailySummaryByMealTimes(
            @PathVariable Long userId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        DailySummariesByMealTimeResponseDto dto = adminUserDailySummaryService.getUserDailySummaryByMealTimes(userId, date);
        SuccessType successType = SuccessType.GET_DAILY_MACROS_SUMMARIES_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, dto));
    }

}
