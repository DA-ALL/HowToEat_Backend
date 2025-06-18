package com.daall.howtoeat.admin.user.dto;

import com.daall.howtoeat.common.enums.UserGoal;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AdminUserDetailResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDate birth;
    private final String profileImageUrl;
    private final Integer targetKcal;
    private final UserGoal userGoal;
    private final Integer streakDays;

    public AdminUserDetailResponseDto(User user, UserTarget userTarget, Integer streakDays) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birth = user.getBirth();
        this.profileImageUrl = user.getProfileImageUrl();
        this.targetKcal = userTarget.getKcal().intValue();
        this.userGoal = userTarget.getGoal();
        this.streakDays = streakDays;
    }
}
