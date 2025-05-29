package com.daall.howtoeat.client.userdailysummary;

import com.daall.howtoeat.client.user.UserTargetService;
import com.daall.howtoeat.client.userdailysummary.dto.DailyConsumedMacrosResponseDto;
import com.daall.howtoeat.client.userdailysummary.dto.DailyKcalResponseDto;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

        UserDailySummary userDailySummary = userDailySummaryRepository.findByUserAndCreatedAtBetween(user, start, end);

        return new DailyConsumedMacrosResponseDto(userDailySummary);
    }
}
