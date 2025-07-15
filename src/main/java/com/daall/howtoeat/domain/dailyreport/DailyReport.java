package com.daall.howtoeat.domain.dailyreport;

import com.daall.howtoeat.admin.dailyreport.dto.ConsumedFoodStatisticsDto;
import com.daall.howtoeat.admin.dailyreport.dto.FoodStatisticsDto;
import com.daall.howtoeat.admin.dailyreport.dto.UserStatisticsDto;
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
    private Long todayRegisteredUserCount;

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

    // 기본값 넣어주기 위한 생성자
    public DailyReport(int number) {
        this.totalUserCount = 0L;
        this.todayRegisteredUserCount = 0L;
        this.maleUserCount = 0L;
        this.femaleUserCount = 0L;
        this.nextGymMemberCount = 0L;
        this.totalFoodCount = 0L;
        this.totalAdminFoodCount = 0L;
        this.totalUserFoodCount = 0L;
        this.totalConsumedFoodCount = 0L;
        this.todayConsumedFoodCount = 0L;
    }

    public void update(UserStatisticsDto userStatisticsDto, FoodStatisticsDto foodStatisticsDto, ConsumedFoodStatisticsDto consumedFoodStatisticsDto) {
        this.totalUserCount = userStatisticsDto.getTotalUserCount();
        this.todayRegisteredUserCount = userStatisticsDto.getTodayRegisteredUserCount();
        this.maleUserCount = userStatisticsDto.getMaleUserCount();
        this.femaleUserCount = userStatisticsDto.getFemaleUserCount();
        this.nextGymMemberCount = userStatisticsDto.getNextGymMemberCount();

        this.totalFoodCount = foodStatisticsDto.getTotalFoodCount();
        this.totalAdminFoodCount = foodStatisticsDto.getTotalAdminFoodCount();
        this.totalUserFoodCount = foodStatisticsDto.getTotalUserFoodCount();

        this.totalConsumedFoodCount = consumedFoodStatisticsDto.getTotalConsumedFoodCount();
        this.todayConsumedFoodCount = consumedFoodStatisticsDto.getTodayConsumedFoodCount();
    }
}
