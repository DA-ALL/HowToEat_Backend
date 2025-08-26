package com.daall.howtoeat.client.usesrmealschedule.dto;

import com.daall.howtoeat.domain.user.UserMealSchedule;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class UserMealScheduleResponseDto {
    private LocalTime breakfastTime;
    private LocalTime lunchTime;
    private LocalTime dinnerTime;

    public UserMealScheduleResponseDto(UserMealSchedule userMealSchedule) {
        this.breakfastTime = userMealSchedule.getBreakfastTime();
        this.lunchTime = userMealSchedule.getLunchTime();
        this.dinnerTime = userMealSchedule.getDinnerTime();
    }
}
