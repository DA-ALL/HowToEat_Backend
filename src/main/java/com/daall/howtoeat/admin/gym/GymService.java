package com.daall.howtoeat.admin.gym;

import com.daall.howtoeat.admin.gym.dto.GymRequestDto;
import com.daall.howtoeat.admin.gym.dto.GymResponseDto;
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

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;

    public Page<GymResponseDto> getGyms(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Gym> gyms;

        System.out.println("name: " + name);
        if (name == null || name.trim().isEmpty()) {
            gyms = gymRepository.findAll(pageable);
        } else {
            gyms = gymRepository.findByNameContainingIgnoreCase(name.trim(), pageable);
        }

        return gyms.map(GymResponseDto::new);
    }

    public GymResponseDto getGym(Long gymId) {
        Gym gym = gymRepository.findById(gymId).orElseThrow(
            ()-> new CustomException(ErrorType.NOT_FOUND_GYM)
        );

        return new GymResponseDto(gym);
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
