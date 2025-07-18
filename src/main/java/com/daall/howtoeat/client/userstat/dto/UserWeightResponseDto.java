package com.daall.howtoeat.client.userstat.dto;

import com.daall.howtoeat.domain.user.UserStat;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
public class UserWeightResponseDto {
    private final Double weight;
    private final LocalDate createdAt;

    public UserWeightResponseDto(UserStat userStat) {
        this.weight = userStat.getWeight();
        this.createdAt = userStat.getWeightRecordedAt();
    }
}
