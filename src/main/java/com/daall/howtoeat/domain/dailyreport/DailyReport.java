package com.daall.howtoeat.domain.dailyreport;

import com.daall.howtoeat.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "daily_reports")
public class DailyReport extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long totalUserCount;

    @Column(nullable = false)
    private Long todayRegistUserCount;

    @Column(nullable = false)
    private Long maleUserCount;

    @Column(nullable = false)
    private Long femaleUserCount;

    @Column(nullable = false)
    private Long nextGymMemberCount;

    @Column(nullable = false)
    private Long totalFoodCount;

    @Column(nullable = false)
    private Long totalConsumedFoodCount;

    @Column(nullable = false)
    private Long todayConsumedFoodCount;

    @Column(nullable = false)
    private Long totalAdminFoodCount;

    @Column(nullable = false)
    private Long totalUserFoodCount;
}
