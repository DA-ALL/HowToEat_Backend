package com.daall.howtoeat.client.user.dto;

import com.daall.howtoeat.domain.user.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserSignupDateResponseDto {
    private LocalDate createdAt;

    public UserSignupDateResponseDto(User loginUser) {
        this.createdAt = LocalDate.from(loginUser.getCreatedAt());
    }
}
