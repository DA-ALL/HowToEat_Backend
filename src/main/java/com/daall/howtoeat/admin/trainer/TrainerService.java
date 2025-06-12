package com.daall.howtoeat.admin.trainer;

import com.daall.howtoeat.admin.gym.GymService;
import com.daall.howtoeat.admin.gym.dto.GymResponseDto;
import com.daall.howtoeat.admin.trainer.dto.TrainerRequestDto;
import com.daall.howtoeat.admin.trainer.dto.TrainerResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.pt.Gym;
import com.daall.howtoeat.domain.pt.Trainer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final GymService gymService;

    public Page<TrainerResponseDto> getTrainers(int page, int size, String name, String gymName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        if (gymName != null && !gymName.trim().isEmpty() && !gymName.equals("all")) {
            Optional<Gym> gym = gymService.getOptionalGymEntity(gymName);
            if (gym.isEmpty()) {
                // Gym 이름으로 검색했는데 존재하지 않는 경우 → 빈 결과 리턴
                return Page.empty(pageable);
            }

            Long gymId = gym.get().getId();

            Page<Trainer> trainers = trainerRepository.searchTrainers(name, gymId, pageable);
            return trainers.map(trainer -> new TrainerResponseDto(trainer, new GymResponseDto(trainer.getGym()), 0));
        }

        // gymName 조건이 없으면 전체 대상에서 검색
        Page<Trainer> trainers = trainerRepository.searchTrainers(name, null, pageable);
        return trainers.map(trainer -> new TrainerResponseDto(trainer, new GymResponseDto(trainer.getGym()), 0));
    }

    public TrainerResponseDto getTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );

        return new TrainerResponseDto(trainer, new GymResponseDto(trainer.getGym()), 0);
    }

    public void createTrainer(String name, Long gymId, MultipartFile image) {
        // TODO: 이미지 처리
        String imageUrl = "";
        System.out.println("이미지 파일: " + image);

        Gym gym = gymService.getGymEntity(gymId);

        Trainer trainer = new Trainer(gym, name, imageUrl);

        trainerRepository.save(trainer);
    }

    @Transactional
    public void updateTrainer(Long trainerId, String name, Long gymId, MultipartFile image) {
        // TODO: 이미지 처리
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );

        Gym gym = gymService.getGymEntity(gymId);
        String imageUrl = "";
        trainer.update(gym, name, imageUrl);
    }

    @Transactional
    public void deleteTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );

        trainerRepository.delete(trainer);
    }
}
