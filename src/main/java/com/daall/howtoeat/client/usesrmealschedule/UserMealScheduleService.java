package com.daall.howtoeat.client.usesrmealschedule;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserMealSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMealScheduleService {
    private final UserMealScheduleRepository userMealScheduleRepository;

    public void createUserMealSchedule(User user) {
        UserMealSchedule userMealSchedule = new UserMealSchedule();

        userMealScheduleRepository.save();
    }
}
