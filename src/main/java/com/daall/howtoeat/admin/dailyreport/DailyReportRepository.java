package com.daall.howtoeat.admin.dailyreport;

import com.daall.howtoeat.domain.dailyreport.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    Optional<DailyReport> findTopByOrderByCreatedAtDesc();

    List<DailyReport> findAllByCreatedAtBetweenOrderByCreatedAtAsc(LocalDateTime localDateTime, LocalDateTime localDateTime1);

    Optional<DailyReport> findFirstByCreatedAtBetween(LocalDateTime todayStart, LocalDateTime todayEnd);
}
