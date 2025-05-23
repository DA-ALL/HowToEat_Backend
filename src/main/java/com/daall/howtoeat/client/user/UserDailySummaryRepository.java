package com.daall.howtoeat.client.user;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserDailySummaryRepository {
    List<UserDailySummary> findAllByUserAndCreatedAtBetween (User user, LocalDateTime start, LocalDateTime end);
}
