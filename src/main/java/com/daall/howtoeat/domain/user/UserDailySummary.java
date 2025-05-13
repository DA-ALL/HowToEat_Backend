package com.daall.howtoeat.domain.user;

import com.daall.howtoeat.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_daily_summaries")
public class UserDailySummary extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // TOTAL 전체 //
    @Column(nullable = false)
    private Double totalKcal;

    @Column(nullable = false)
    private Double totalCarbo;

    @Column(nullable = false)
    private Double totalProtein;

    @Column(nullable = false)
    private Double totalFat;


    // BREAKFAST 아침 //
    @Column(nullable = false)
    private Double breakfastKcal;

    @Column(nullable = false)
    private Double breakfastCarbo;

    @Column(nullable = false)
    private Double breakfastProtein;

    @Column(nullable = false)
    private Double breakfastFat;


    // LUNCH 점심 //
    @Column(nullable = false)
    private Double lunchKcal;

    @Column(nullable = false)
    private Double lunchCarbo;

    @Column(nullable = false)
    private Double lunchProtein;

    @Column(nullable = false)
    private Double lunchFat;


    // DINNER 저녁 //
    @Column(nullable = false)
    private Double dinnerKcal;

    @Column(nullable = false)
    private Double dinnerCarbo;

    @Column(nullable = false)
    private Double dinnerProtein;

    @Column(nullable = false)
    private Double dinnerFat;


    // SNACK 간식 //
    @Column(nullable = false)
    private Double snackKcal;

    @Column(nullable = false)
    private Double snackCarbo;

    @Column(nullable = false)
    private Double snackProtein;

    @Column(nullable = false)
    private Double snackFat;


}
