package com.daall.howtoeat.admin.trainer.dto;

import com.daall.howtoeat.admin.gym.dto.GymResponseDto;
import com.daall.howtoeat.domain.pt.Gym;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class TrainerResponseDto {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final GymResponseDto gym;
    private final Integer memberCount;
    private final LocalDate createdAt;
}
