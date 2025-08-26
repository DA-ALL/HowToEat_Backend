package com.daall.howtoeat.client.usesrmealschedule;

import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.client.usesrmealschedule.dto.UserMealScheduleResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserMealSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMealScheduleService {
    private final UserMealScheduleRepository userMealScheduleRepository;

    public void createUserMealSchedule(User user, SignupRequestDto requestDto) {
        UserMealSchedule userMealSchedule = new UserMealSchedule(user, requestDto);

        userMealScheduleRepository.save(userMealSchedule);
    }

    public UserMealScheduleResponseDto getUserMealSchedule(User loginUser) {
        UserMealSchedule userMealSchedule = userMealScheduleRepository.findByUser(loginUser).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_MEAL_SCHEDULE));

        return new UserMealScheduleResponseDto(userMealSchedule);
    }
}
