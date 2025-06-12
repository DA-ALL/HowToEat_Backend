package com.daall.howtoeat.admin.gym.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class GymWithTrainerCountResponseDto {
    private final Long id;
    private final String name;
    private final Integer trainerCount;
    private final LocalDate createdAt;

    public GymWithTrainerCountResponseDto(Long id, String name, Integer trainerCount, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.trainerCount = trainerCount;
        this.createdAt = createdAt.toLocalDate();
    }
}

