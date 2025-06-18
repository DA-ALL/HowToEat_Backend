package com.daall.howtoeat.client.userdailysummary;

import com.daall.howtoeat.client.user.UserTargetService;
import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosByMealTimeResponseDto;
import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosResponseDto;
import com.daall.howtoeat.client.userdailysummary.dto.DailyKcalResponseDto;
import com.daall.howtoeat.client.userdailysummary.dto.DailyNutritionSummary;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDailySummaryService {
    private final UserDailySummaryRepository userDailySummaryRepository;
    private final UserTargetService userTargetService;

    public ArrayList<DailyKcalResponseDto> getDailyKcalSummaries(User user, LocalDate start_date, LocalDate end_date) {
        LocalDateTime start = start_date.atStartOfDay();
        LocalDateTime end = end_date.atTime(23, 59, 59);

        List<UserDailySummary> dailySummaries = userDailySummaryRepository.findAllByUserAndCreatedAtBetween(user, start, end);

        ArrayList<DailyKcalResponseDto> responseDto = new ArrayList<>();

        for (UserDailySummary dailySummary : dailySummaries) {
            LocalDate date = dailySummary.getCreatedAt().toLocalDate();
            Double targetKcal = dailySummary.getUserTarget().getKcal();
            Double consumedKcal = dailySummary.getTotalKcal();

            responseDto.add(new DailyKcalResponseDto(date, targetKcal, consumedKcal));
        }

        return responseDto;
    }

    public DailyConsumedMacrosResponseDto getDailyMacrosSummary(User user, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        UserDailySummary summary = userDailySummaryRepository.findByUserAndCreatedAtBetween(user, start, end).orElse(null);
        UserTarget target = userTargetService.getLatestTargetBeforeOrOn(user, date);

        if (summary == null) {
            // 음식을 섭취한 적이 없으면 target만 넘김 -> 데이터가 없기 때문에 목표 탄단지만 보내야함
            return new DailyConsumedMacrosResponseDto(target, date);
        }

        return new DailyConsumedMacrosResponseDto(summary);
    }


    /**
     * 선택한 사용자, 날짜, 끼니 정보에 따라 해당 시간대의 목표 및 섭취 탄수화물/단백질/지방(macros)을 반환
     *
     * ※ 목표(macros)는 UserDailySummary에 저장된 UserTarget을 기준으로 추
     *     만약 해당 날짜에 UserDailySummary가 존재하지 않을 경우 예외가 발생
     *
     * @param user     대상 사용자
     * @param date     조회할 날짜
     * @param mealTime 조회할 끼니 (아침, 점심, 저녁, 간식)
     * @return 선택한 날짜와 끼니에 대한 목표 및 섭취 탄단지 정보 DTO
     */
    public DailyConsumedMacrosByMealTimeResponseDto getMacrosByUserDateAndMealTime(User user, LocalDate date, MealTime mealTime) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        UserDailySummary summary = userDailySummaryRepository.findByUserAndCreatedAtBetween(user, start, end).orElse(null);
        UserTarget target = userTargetService.getLatestTargetBeforeOrOn(user, date);

        if(summary == null) {
            // 음식을 섭취한 적이 없으면 target만 넘김 -> 데이터가 없기 때문에 목표 탄단지만 보내야함
            return new DailyConsumedMacrosByMealTimeResponseDto(target, mealTime);
        }

        return new DailyConsumedMacrosByMealTimeResponseDto(summary, mealTime);
    }


    /**
     * 이미 기록되어있는 userDailySummary가 있는지 확인
     * @param loginUser     대상 사용자
     * @param date     조회할 날짜
     * @return 오늘 날짜의 userDailySummary
     */
    public Optional<UserDailySummary> findUserDailySummary(User loginUser, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        return userDailySummaryRepository.findByUserAndCreatedAtBetween(loginUser, start, end);
    }


    /**
     * 처음 음식을 등록했을 때, userDailySummary 생성
     * @param loginUser                 대상 사용자
     * @param userTarget                음식 등록하는 날짜의 유저 타겟
     * @param dailyNutritionSummary     consumedFood에 기록되어있는 모든 음식들의 전체 칼로리 / 탄 / 단 / 지
     */
    public void createUserDailySummary(User loginUser, UserTarget userTarget, DailyNutritionSummary dailyNutritionSummary) {
        UserDailySummary userDailySummary = new UserDailySummary();
        userDailySummary.setData(loginUser, userTarget, dailyNutritionSummary);
        userDailySummaryRepository.save(userDailySummary);
    }

    public int getStreakDays(User loginUser){
        Optional<UserDailySummary> userDailySummary = this.findUserDailySummary(loginUser, LocalDate.now());
        int result = 0;

        if(userDailySummary.isPresent()){
            result = userDailySummaryRepository.findConsecutiveDays(loginUser.getId(), LocalDate.now());
        } else {
            result = userDailySummaryRepository.findConsecutiveDays(loginUser.getId(), LocalDate.now().minusDays(1));
        }

        return result;
    }
}
