package com.daall.howtoeat.admin.gym;

import com.daall.howtoeat.admin.gym.dto.GymWithTrainerCountResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GymRepositoryQuery {
    Page<GymWithTrainerCountResponseDto> getGymsWithTrainerCount(Pageable pageable, String name);
}
