package com.daall.howtoeat.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AdminAccountRequestDto {
    @NotBlank
    private String accountId;
    @NotBlank
    private String password;
}
