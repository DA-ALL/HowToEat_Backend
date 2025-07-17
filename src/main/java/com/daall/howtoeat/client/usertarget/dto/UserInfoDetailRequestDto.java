package com.daall.howtoeat.client.usertarget.dto;

import com.daall.howtoeat.common.enums.UserActivityLevel;
import com.daall.howtoeat.common.enums.UserGoal;
import lombok.Getter;

@Getter
public class UserInfoDetailRequestDto {
    private UserGoal userGoal;
    private UserActivityLevel userActivityLevel;
}
