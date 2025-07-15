package com.daall.howtoeat.client.user.dto;

import com.daall.howtoeat.common.enums.UserActivityLevel;
import com.daall.howtoeat.common.enums.UserGoal;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStat;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.Getter;


// 유저 세부 정보 DTO
@Getter
public class UserInfoDetailResponseDto {
    private String name;
    private String email;
    private String imageUrl;
    private UserGoal userGoal;
    private UserActivityLevel userActivityLevel;
    private Double height;
    private Double weight;

    public UserInfoDetailResponseDto(User user, UserTarget userTarget, UserStat userStat) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.imageUrl = user.getProfileImageUrl();
        this.userGoal = userTarget.getGoal();
        this.userActivityLevel = userTarget.getActivityLevel();
        this.height = userStat.getHeight();
        this.weight = userStat.getWeight();
    }
}
