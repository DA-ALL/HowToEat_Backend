package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class UserDailySummaryController {
    private final UserDailySummaryService userDailySummaryService;

    @GetMapping("/daily-summary/kcals")
    public ResponseEntity<String> getDailyKcalSummary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        User loginUser = userDetails.getUser();
        userDailySummaryService.getDailyKcalSummaries(loginUser, startDate, endDate);

        return ResponseEntity.ok("데이터가 전송되었습니다.");
    }
}
