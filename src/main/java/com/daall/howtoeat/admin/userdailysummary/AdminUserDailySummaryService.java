package com.daall.howtoeat.admin.userdailysummary;

import com.daall.howtoeat.admin.userdailysummary.dto.DailyCaloriesSummaryDto;
import com.daall.howtoeat.admin.userdailysummary.dto.DailyMacrosWithDatesResponseDto;
import com.daall.howtoeat.client.user.UserService;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryRepository;
import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosResponseDto;
import com.daall.howtoeat.client.usertarget.UserTargetService;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserDailySummaryService {
    private final UserDailySummaryRepository userDailySummaryRepository;
    private final UserService userService;
    private final UserTargetService userTargetService;

    public Page<DailyCaloriesSummaryDto> getUserDailyCalories(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<UserDailySummary> userDailySummaries = userDailySummaryRepository.findAllByUserId(userId, pageable);

        return userDailySummaries.map(DailyCaloriesSummaryDto::new);
    }

    public DailyMacrosWithDatesResponseDto getUserDailySummaryMacrosWithDates(Long userId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end   = date.atTime(LocalTime.MAX);

        User user = userService.getUser(userId);

        // 현재 날짜의 매크로
        UserDailySummary summary = userDailySummaryRepository.findByUserAndCreatedAtBetween(user, start, end).orElse(null);
        UserTarget target = userTargetService.getLatestTargetBeforeOrOn(user, date);
//        System.out.println(macros.getDate());
//        System.out.println(target.getCarbo());

        DailyConsumedMacrosResponseDto macros =
                (summary == null) ? new DailyConsumedMacrosResponseDto(target, date)
                        : new DailyConsumedMacrosResponseDto(summary);



        // 이전 / 다음 날짜 조회
        String prevDate = userDailySummaryRepository
                .findTopByUserAndCreatedAtBeforeOrderByCreatedAtDesc(user, start)
                .map(s -> s.getCreatedAt().toLocalDate().toString())
                .orElse(null);
        System.out.println("TESTSTETT");


        String nextDate = userDailySummaryRepository
                .findTopByUserAndCreatedAtAfterOrderByCreatedAtAsc(user, end)
                .map(s -> s.getCreatedAt().toLocalDate().toString())
                .orElse(null);

        List<String> dates = Arrays.asList(prevDate, nextDate);
        return new DailyMacrosWithDatesResponseDto(dates, macros);
    }

}
