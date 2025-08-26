package com.daall.howtoeat.client.usesrmealschedule;

import com.daall.howtoeat.client.user.dto.SignupRequestDto;
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
}
