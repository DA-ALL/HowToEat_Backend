package com.daall.howtoeat.admin.user;

import com.daall.howtoeat.admin.user.dto.DailyCaloriesSummaryDto;
import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminUserDailySummaryController {
    private final AdminUserDailySummaryService adminUserDailySummaryService;

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
}
