package com.daall.howtoeat.admin.gym;

import com.daall.howtoeat.admin.gym.dto.GymWithTrainerCountResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GymRepositoryQuery {
    Page<GymWithTrainerCountResponseDto> getGymsWithTrainerCount(Pageable pageable, String name);
    List<GymWithTrainerCountResponseDto> getAllGyms();
}
