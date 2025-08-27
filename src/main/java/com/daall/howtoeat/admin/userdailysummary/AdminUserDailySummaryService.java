package com.daall.howtoeat.admin.userdailysummary;

import com.daall.howtoeat.admin.consumedfood.dto.ConsumedFoodResponseDto;
import com.daall.howtoeat.admin.userdailysummary.dto.DailyCaloriesSummaryDto;
import com.daall.howtoeat.admin.userdailysummary.dto.DailyMacrosWithDatesResponseDto;
import com.daall.howtoeat.admin.userdailysummary.dto.DailySummariesByMealTimeResponseDto;
import com.daall.howtoeat.client.user.UserService;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryRepository;
import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosByMealTimeResponseDto;
import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosResponseDto;
import com.daall.howtoeat.client.usertarget.UserTargetService;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
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
import java.util.ArrayList;
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

    /**
     * 유저 dailySummary 정보 + 이전 or 다음 날짜 조회
     *
     * @PathVariable userId 조회할 유저 아이디
     * @PathVariable date 조회할 날짜
     * @return ResponseEntity<ResponseDataDto<DailyMacrosWithDatesResponseDto>>
     *
     */
    public DailyMacrosWithDatesResponseDto getUserDailySummaryMacrosWithDates(Long userId, LocalDate date) {

        User user = userService.getUser(userId);

        // 현재 날짜의 매크로
        UserDailySummary summary = userDailySummaryRepository.findByUserAndRegisteredAt(user, date).orElse(null);
        UserTarget target = userTargetService.getLatestTargetBeforeOrOn(user, date);

        DailyConsumedMacrosResponseDto macros =
                (summary == null) ? new DailyConsumedMacrosResponseDto(target, date)
                        : new DailyConsumedMacrosResponseDto(summary);


        // 이전 / 다음 날짜 조회
        String prevDate = userDailySummaryRepository
                .findTopByUserAndRegisteredAtBeforeOrderByRegisteredAtDesc(user, date)
                .map(s -> s.getRegisteredAt().toString())
                .orElse(null);

        String nextDate = userDailySummaryRepository
                .findTopByUserAndRegisteredAtAfterOrderByRegisteredAtAsc(user, date)
                .map(s -> s.getRegisteredAt().toString())
                .orElse(null);

        List<String> dates = Arrays.asList(prevDate, nextDate);
        return new DailyMacrosWithDatesResponseDto(dates, macros);
    }

    /**
     * 유저 dailySummary 정보 + 이전 or 다음 날짜 조회
     *
     * @PathVariable userId 조회할 유저 아이디
     * @PathVariable date 조회할 날짜
     * @return ResponseEntity<ResponseDataDto<DailyMacrosWithDatesResponseDto>>
     *
     */
    public DailySummariesByMealTimeResponseDto getUserDailySummaryByMealTimes(Long userId, LocalDate date) {
//        LocalDateTime start = date.atStartOfDay();
//        LocalDateTime end = date.atTime(23, 59, 59);
        User user = userService.getUser(userId);

        UserDailySummary summary = userDailySummaryRepository.findByUserAndRegisteredAt(user, date).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_DAILY_SUMMARY));

        DailyConsumedMacrosByMealTimeResponseDto breakfast = new DailyConsumedMacrosByMealTimeResponseDto(summary, MealTime.BREAKFAST);
        DailyConsumedMacrosByMealTimeResponseDto lunch = new DailyConsumedMacrosByMealTimeResponseDto(summary, MealTime.LUNCH);
        DailyConsumedMacrosByMealTimeResponseDto dinner = new DailyConsumedMacrosByMealTimeResponseDto(summary, MealTime.DINNER);
        DailyConsumedMacrosByMealTimeResponseDto snack = new DailyConsumedMacrosByMealTimeResponseDto(summary, MealTime.SNACK);

        return new DailySummariesByMealTimeResponseDto(breakfast, lunch, dinner, snack);
    }


}
