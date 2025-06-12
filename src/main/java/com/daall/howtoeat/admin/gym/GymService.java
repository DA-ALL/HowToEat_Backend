package com.daall.howtoeat.admin.gym;

import com.daall.howtoeat.admin.gym.dto.GymRequestDto;
import com.daall.howtoeat.admin.gym.dto.GymResponseDto;
import com.daall.howtoeat.admin.gym.dto.GymWithTrainerCountResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.pt.Gym;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;

    public Page<GymWithTrainerCountResponseDto> getGyms(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        return gymRepository.getGymsWithTrainerCount(pageable, name);
    }

    public List<GymWithTrainerCountResponseDto> getAllGyms() {
        return gymRepository.getAllGyms();
    }

    public GymResponseDto getGym(Long gymId) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(
            ()-> new CustomException(ErrorType.NOT_FOUND_GYM)
        );

        return new GymResponseDto(gym);
    }

    public Optional<Gym> getOptionalGymEntity(String name){
        return gymRepository.findByName(name);
    }

    public Gym getGymEntity(Long gymId) {
        return gymRepository.findById(gymId).orElseThrow(
            () -> new CustomException(ErrorType.NOT_FOUND_GYM)
        );
    }

    public void createGym(GymRequestDto requestDto) {
        if(gymRepository.existsByName(requestDto.getName())){
            throw new CustomException(ErrorType.ALREADY_EXISTS_GYM_NAME);
        }
        Gym gym = new Gym(requestDto);

        gymRepository.save(gym);
    }

    @Transactional
    public void updateGym(Long gymId, GymRequestDto requestDto) {
        if(gymRepository.existsByName(requestDto.getName())){
            throw new CustomException(ErrorType.ALREADY_EXISTS_GYM_NAME);
        }

        Gym gym = gymRepository.findById(gymId).orElseThrow(
            ()-> new CustomException(ErrorType.NOT_FOUND_GYM)
        );

        gym.updateGym(requestDto);
    }

    @Transactional
    public void deleteGym(Long gymId) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(
                ()-> new CustomException(ErrorType.NOT_FOUND_GYM)
        );

        gymRepository.delete(gym);
    }

}
