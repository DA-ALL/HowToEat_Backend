package com.daall.howtoeat.client.user;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserDailySummaryRepository extends JpaRepository<UserDailySummary, Long> {
    List<UserDailySummary> findAllByUserAndCreatedAtBetween (User user, LocalDateTime start, LocalDateTime end);
}
