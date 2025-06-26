package com.daall.howtoeat.admin.dailyreport;

import com.daall.howtoeat.domain.dailyreport.DailyReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DailyReportRepository extends JpaRepository<DailyReport, Long> {
    Optional<DailyReport> findTopByOrderByCreatedAtDesc();
}
