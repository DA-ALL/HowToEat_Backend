package com.daall.howtoeat.admin.user;

import com.daall.howtoeat.admin.user.dto.DailyCaloriesSummaryDto;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryRepository;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserDailySummaryService {
    private final UserDailySummaryRepository userDailySummaryRepository;

    public Page<DailyCaloriesSummaryDto> getUserDailyCalories(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<UserDailySummary> userDailySummaries = userDailySummaryRepository.findAllByUserId(userId, pageable);

        return userDailySummaries.map(DailyCaloriesSummaryDto::new);
    }
}
