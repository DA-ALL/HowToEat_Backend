package com.daall.howtoeat.admin.account.dto;

import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.domain.user.User;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AdminAccountResponseDto {
    private final Long id;
    private final String accountId;
    private final UserRole userRole;
    private final LocalDate createdAt;


    public AdminAccountResponseDto(User user) {
        this.id = user.getId();
        this.accountId = user.getEmail();
        this.userRole = user.getUserRole();
        this.createdAt = user.getCreatedAt().toLocalDate();
    }
}
