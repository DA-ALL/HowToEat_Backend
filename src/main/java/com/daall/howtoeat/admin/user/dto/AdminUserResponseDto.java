package com.daall.howtoeat.admin.user.dto;

import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.SignupProvider;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.enums.UserStatus;
import com.daall.howtoeat.domain.user.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AdminUserResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final Gender gender;
    private final LocalDate birth;
    private final boolean isNextGym;
    private final String profileImageUrl;
    private final UserRole userRole;
    private final UserStatus userStatus;
    private final SignupProvider signup_provider;
    private final String refreshToken;
    private final LocalDate createdAt;

    public AdminUserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.birth = user.getBirth();
        this.isNextGym = user.isNextGym();
        this.profileImageUrl = user.getProfileImageUrl();
        this.userRole = user.getUserRole();
        this.userStatus = user.getUserStatus();
        this.signup_provider = user.getSignup_provider();
        this.refreshToken = user.getRefreshToken();
        this.createdAt = user.getCreatedAt().toLocalDate();
    }
}
