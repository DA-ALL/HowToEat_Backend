package com.daall.howtoeat.client.user;

import com.daall.howtoeat.client.user.dto.DailyKcalResponseDto;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDailySummaryService {
    private UserDailySummaryRepository userDailySummaryRepository;
    private UserTargetService userTargetService;

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

        System.out.println("시작");
        for (UserDailySummary dailySummary : dailySummaries) {
            System.out.println(dailySummary.toString());
            System.out.println(dailySummary.getUserTarget().toString());
        }
        System.out.println("끝");

        return responseDto;
    }
}
