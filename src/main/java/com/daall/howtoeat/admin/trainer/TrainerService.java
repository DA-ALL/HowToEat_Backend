package com.daall.howtoeat.admin.trainer;

import com.daall.howtoeat.admin.gym.dto.GymResponseDto;
import com.daall.howtoeat.admin.trainer.dto.TrainerResponseDto;
import com.daall.howtoeat.domain.pt.Gym;
import com.daall.howtoeat.domain.pt.Trainer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;

    public Page<TrainerResponseDto> getTrainers(int page, int size, String name, String gym) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Trainer> trainers;

        return null;
    }
}
