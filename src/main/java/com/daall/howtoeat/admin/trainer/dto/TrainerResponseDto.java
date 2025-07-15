package com.daall.howtoeat.admin.trainer.dto;

import com.daall.howtoeat.admin.gym.dto.GymResponseDto;
import com.daall.howtoeat.domain.pt.Gym;
import com.daall.howtoeat.domain.pt.Trainer;
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
    private final Long memberCount;
    private final LocalDate createdAt;

    public TrainerResponseDto(Trainer trainer, GymResponseDto gymResponseDto, Long memberCount){
        this.id = trainer.getId();
        this.name = trainer.getName();
        this.imageUrl = trainer.getImageUrl();
        this.gym = gymResponseDto;
        this.memberCount = memberCount;
        this.createdAt = trainer.getCreatedAt().toLocalDate();
    }
}
