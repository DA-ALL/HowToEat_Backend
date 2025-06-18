package com.daall.howtoeat.client.user.dto;

import com.daall.howtoeat.common.enums.UserGoal;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStat;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.Getter;

@Getter
public class UserInfoBasicResponseDto {
    private String name;
    private String imageUrl;
    private UserGoal userGoal;
    private Double targetKcal;
    private Integer streakDay;

    public UserInfoBasicResponseDto(User loginUser, UserTarget userTarget, int streakDay) {
        this.name = loginUser.getName();
        this.imageUrl = loginUser.getProfileImageUrl();
        this.userGoal = userTarget.getGoal();
        this.targetKcal = userTarget.getKcal();
        this.streakDay = streakDay;
    }
}
