package com.daall.howtoeat.client.userdailysummary;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDailySummaryRepository extends JpaRepository<UserDailySummary, Long> {
    List<UserDailySummary> findAllByUserAndCreatedAtBetween (User user, LocalDateTime start, LocalDateTime end);
    Optional<UserDailySummary> findByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);
    // 이전(과거) 중 가장 최신
    Optional<UserDailySummary> findTopByUserAndCreatedAtBeforeOrderByCreatedAtDesc(User user, LocalDateTime before);
    // 다음(미래) 중 가장 빠른
    Optional<UserDailySummary> findTopByUserAndCreatedAtAfterOrderByCreatedAtAsc(User user, LocalDateTime after);

    //회원탈퇴 시 연관관계 끊기
    void deleteAllByUser(User user);

    @Query(value = """
    SELECT COUNT(*) AS consecutive_days
    FROM (
        SELECT created_at,
               ROW_NUMBER() OVER (ORDER BY DATE(created_at) DESC) AS rn,
               DATEDIFF(:baseDate, DATE(created_at)) AS diff
        FROM user_daily_summaries
        WHERE user_id = :userId
          AND DATE(created_at) <= :baseDate
    ) AS sub
    WHERE diff = rn - 1
    """, nativeQuery = true)
    int findConsecutiveDays(@Param("userId") Long userId, @Param("baseDate") LocalDate baseDate);

    Page<UserDailySummary> findAllByUserId(Long userId, Pageable pageable);
}
