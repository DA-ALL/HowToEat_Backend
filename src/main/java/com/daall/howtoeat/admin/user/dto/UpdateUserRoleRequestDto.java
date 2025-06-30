package com.daall.howtoeat.admin.user.dto;

import com.daall.howtoeat.common.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateUserRoleRequestDto {
    @NotNull(message = "유저 권한이 없습니다. 다시 시도해주세요.")
    private UserRole userRole;
}
