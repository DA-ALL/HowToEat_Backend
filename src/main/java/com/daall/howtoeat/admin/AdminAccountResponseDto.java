package com.daall.howtoeat.admin;

import com.daall.howtoeat.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminAccountResponseDto {
    private Long id;
    private String accountId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public AdminAccountResponseDto(User user) {
        this.id = user.getId();
        this.accountId = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}
