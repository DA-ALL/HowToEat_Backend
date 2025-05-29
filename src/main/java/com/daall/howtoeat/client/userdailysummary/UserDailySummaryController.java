package com.daall.howtoeat.client.userdailysummary;

import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosResponseDto;
import com.daall.howtoeat.client.userdailysummary.dto.DailyKcalResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class UserDailySummaryController {
    private final UserDailySummaryService userDailySummaryService;

    @GetMapping("/daily-summary/kcals")
    public ResponseEntity<ResponseDataDto<ArrayList<DailyKcalResponseDto>>> getDailyKcalSummary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        User loginUser = userDetails.getUser();
        ArrayList<DailyKcalResponseDto> responseDtos = userDailySummaryService.getDailyKcalSummaries(loginUser, startDate, endDate);

        SuccessType successType = SuccessType.GET_DAILY_KCAL_SUMMARIES_SUSSESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDtos));
    }

    @GetMapping("daily-summary/{date}/macros")
    public ResponseEntity<ResponseDataDto<DailyConsumedMacrosResponseDto>> getDailyMacrosSummary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        User loginUser = userDetails.getUser();
        DailyConsumedMacrosResponseDto responseDto = userDailySummaryService.getDailyMacrosSummary(loginUser, date);

        SuccessType successType = SuccessType.GET_DAILY_KCAL_SUMMARIES_SUSSESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }
}
