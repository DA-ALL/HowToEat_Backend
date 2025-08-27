package com.daall.howtoeat.client.usertarget.dto;

import com.daall.howtoeat.domain.user.UserTarget;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TargetKcalsResponseDto {
    private final Double targetKcal;
    private final LocalDate date;

    public TargetKcalsResponseDto(UserTarget target, LocalDate date) {
        this.targetKcal = target.getKcal();
        this.date = date;
    }
}
