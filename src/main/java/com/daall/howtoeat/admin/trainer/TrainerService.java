package com.daall.howtoeat.admin.trainer;

import com.daall.howtoeat.admin.gym.GymService;
import com.daall.howtoeat.admin.gym.dto.GymResponseDto;
import com.daall.howtoeat.admin.ptmember.PtMemberService;
import com.daall.howtoeat.admin.trainer.dto.TrainerResponseDto;
import com.daall.howtoeat.common.S3Uploader;
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

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final GymService gymService;
    private final PtMemberService ptMemberService;
    private final S3Uploader s3Uploader;

    public Page<TrainerResponseDto> getTrainers(int page, int size, String name, String gymName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        Optional<Long> optionalGymId = Optional.empty();

        if (gymName != null && !gymName.trim().isEmpty() && !gymName.equalsIgnoreCase("all")) {
            Optional<Gym> gym = gymService.getOptionalGymEntity(gymName);
            if (gym.isEmpty()) {
                return Page.empty(pageable); // 존재하지 않는 gymName인 경우
            }
            optionalGymId = Optional.of(gym.get().getId());
        }

        Page<Trainer> trainers = trainerRepository.searchTrainers(name, optionalGymId.orElse(null), pageable);

        List<Long> trainerIds = trainers.getContent().stream()
                .map(Trainer::getId)
                .toList();

        Map<Long, Long> ptMemberCountsByTrainerIds = ptMemberService.getPtMemberCountsByTrainerIds(trainerIds);

        return trainers.map(trainer -> {
            long memberCount = ptMemberCountsByTrainerIds.getOrDefault(trainer.getId(), 0L);
            return new TrainerResponseDto(
                    trainer,
                    new GymResponseDto(trainer.getGym()),
                    memberCount
            );
        });
    }

    public TrainerResponseDto getTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );

        return new TrainerResponseDto(trainer, new GymResponseDto(trainer.getGym()), ptMemberService.getPtMemberCountByTrainerId(trainer.getId()));
    }

    @Transactional
    public void createTrainer(String name, Long gymId, MultipartFile image) throws IOException {
        Gym gym = gymService.getGymEntity(gymId);

        Trainer trainer = new Trainer(gym, name);

        trainerRepository.save(trainer);

        String imageUrl = s3Uploader.upload(image, "trainer_profile_images", trainer.getId());

        trainer.setImageUrl(imageUrl);
    }

    @Transactional
    public void updateTrainer(Long trainerId, String name, Long gymId, MultipartFile image) throws IOException {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );
        Gym gym = gymService.getGymEntity(gymId);

        System.out.println("trainerService 업데이트 시작");
        if(!image.isEmpty()){
            String imageUrl = s3Uploader.upload(image, "trainer_profile_images", trainer.getId());
            // 기존 프로필 이미지 삭제
            s3Uploader.delete(trainer.getImageUrl());
            trainer.setImageUrl(imageUrl);
            System.out.println("trainerService 이미지url: " + imageUrl);
        }

        trainer.update(gym, name);
    }

    @Transactional
    public void deleteTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );

        s3Uploader.delete(trainer.getImageUrl());

        trainerRepository.delete(trainer);
    }

    public Trainer getTrainerById(Long trainerId) {
        return trainerRepository.findById(trainerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );
    }
}
