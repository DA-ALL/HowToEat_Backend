package com.daall.howtoeat.client.usesrmealschedule;

import com.daall.howtoeat.client.usesrmealschedule.dto.UserMealScheduleResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserMealScheduleController {
    private final UserMealScheduleService userMealScheduleService;

    @GetMapping("/users/meal-schedules")
    public ResponseEntity<ResponseDataDto<UserMealScheduleResponseDto>> getUserMealSchedule(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User loginUser = userDetails.getUser();

        SuccessType successType = SuccessType.GET_USER_MEAL_SCHEDULE;
        UserMealScheduleResponseDto responseDto = userMealScheduleService.getUserMealSchedule(loginUser);

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }
}
