package com.daall.howtoeat.admin.user.dto;

import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.domain.user.User;
import lombok.Getter;

@Getter
public class AdminMeResponseDto {
    private final Long id;
    private final String name;
    private final UserRole userRole;
    private final String profileImageUrl;

    public AdminMeResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.userRole = user.getUserRole();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}
