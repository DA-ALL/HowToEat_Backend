package com.daall.howtoeat.domain.user;

import com.daall.howtoeat.client.userdailysummary.dto.DailyNutritionSummary;
import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.common.enums.MealTime;
import com.daall.howtoeat.domain.consumedfood.ConsumedFood;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_target_id", nullable = false)
    private UserTarget userTarget;

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

    //오늘 처음 음식들을 섭취 후 등록했을 때
    public void setData(User loginUser, UserTarget userTarget, DailyNutritionSummary dailyNutritionSummary) {
        this.user = loginUser;
        this.userTarget = userTarget;
        this.totalKcal = dailyNutritionSummary.getTotalKcal();
        this.totalCarbo = dailyNutritionSummary.getTotalCarbo();
        this.totalProtein = dailyNutritionSummary.getTotalProtein();
        this.totalFat = dailyNutritionSummary.getTotalFat();
        this.breakfastKcal = dailyNutritionSummary.getBreakfastKcal();
        this.breakfastCarbo = dailyNutritionSummary.getBreakfastCarbo();
        this.breakfastProtein = dailyNutritionSummary.getBreakfastProtein();
        this.breakfastFat = dailyNutritionSummary.getBreakfastFat();
        this.lunchKcal = dailyNutritionSummary.getLunchKcal();
        this.lunchCarbo = dailyNutritionSummary.getLunchCarbo();
        this.lunchProtein = dailyNutritionSummary.getLunchProtein();
        this.lunchFat = dailyNutritionSummary.getLunchFat();
        this.dinnerKcal = dailyNutritionSummary.getDinnerKcal();
        this.dinnerCarbo = dailyNutritionSummary.getDinnerCarbo();
        this.dinnerProtein = dailyNutritionSummary.getDinnerProtein();
        this.dinnerFat = dailyNutritionSummary.getDinnerFat();
        this.snackKcal = dailyNutritionSummary.getSnackKcal();
        this.snackCarbo = dailyNutritionSummary.getSnackCarbo();
        this.snackProtein = dailyNutritionSummary.getSnackProtein();
        this.snackFat = dailyNutritionSummary.getSnackFat();
    }

    //섭취음식 삭제 시, 해당 음식값 감소
    public void decreaseMacros(ConsumedFood consumedFood) {
        MealTime mealTime = consumedFood.getMealTime();
        this.totalKcal = this.totalKcal - consumedFood.getKcal();
        this.totalCarbo = this.totalCarbo - consumedFood.getCarbo();
        this.totalProtein = this.totalProtein - consumedFood.getProtein();
        this.totalFat = this.totalFat - consumedFood.getFat();

        switch (mealTime) {
            case BREAKFAST -> {
                this.breakfastKcal = round(this.getBreakfastKcal() - consumedFood.getKcal());
                this.breakfastCarbo = round(this.getBreakfastCarbo() - consumedFood.getCarbo());
                this.breakfastProtein = round(this.getBreakfastProtein() - consumedFood.getProtein());
                this.breakfastFat = round(this.getBreakfastFat() - consumedFood.getFat());
            }
            case LUNCH -> {
                this.lunchKcal = round(this.getLunchKcal() - consumedFood.getKcal());
                this.lunchCarbo = round(this.getLunchCarbo() - consumedFood.getCarbo());
                this.lunchProtein = round(this.getLunchProtein() - consumedFood.getProtein());
                this.lunchFat = round(this.getLunchFat() - consumedFood.getFat());
            }
            case DINNER -> {
                this.dinnerKcal = round(this.getDinnerKcal() - consumedFood.getKcal());
                this.dinnerCarbo = round(this.getDinnerCarbo() - consumedFood.getCarbo());
                this.dinnerProtein = round(this.getDinnerProtein() - consumedFood.getProtein());
                this.dinnerFat = round(this.getDinnerFat() - consumedFood.getFat());
            }
            case SNACK -> {
                this.snackKcal = round(this.getSnackKcal() - consumedFood.getKcal());
                this.snackCarbo = round(this.getSnackCarbo() - consumedFood.getCarbo());
                this.snackProtein = round(this.getSnackProtein() - consumedFood.getProtein());
                this.snackFat = round(this.getSnackFat() - consumedFood.getFat());
            }
        }
    }

    //부동소수점 방지 - 소수점 둘째 자리에서 반올림
    private double round(double value) {
        return Math.round(value * 10) / 10.0;
    }
}
