package com.daall.howtoeat.admin.trainer;

import com.daall.howtoeat.admin.gym.GymService;
import com.daall.howtoeat.admin.gym.dto.GymResponseDto;
import com.daall.howtoeat.admin.ptmember.PtMemberService;
import com.daall.howtoeat.admin.trainer.dto.TrainerRequestDto;
import com.daall.howtoeat.admin.trainer.dto.TrainerResponseDto;
import com.daall.howtoeat.admin.trainer.dto.TrainerWithPtMembersResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.pt.Gym;
import com.daall.howtoeat.domain.pt.Trainer;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.SpecialOneToOneType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Trainer getTrainerById(Long trainerId) {
        return trainerRepository.findById(trainerId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_TRAINER)
        );
    }
}
