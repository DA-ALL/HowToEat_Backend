package com.daall.howtoeat.admin.gym.dto;

import com.daall.howtoeat.domain.pt.Gym;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GymResponseDto {
    private final Long id;
    private final String name;
    private final LocalDate createdAt;

    public GymResponseDto(Gym gym) {
        this.id = gym.getId();
        this.name = gym.getName();
        this.createdAt = gym.getCreatedAt().toLocalDate();
    }
}
