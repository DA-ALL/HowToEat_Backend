package com.daall.howtoeat.common.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    MASTER("ROLE_MASTER"),
    ADMIN("ROLE_ADMIN"),
    SUPERUSER("ROLE_SUPERUSER"),
    USER("ROLE_USER");

    private final String authority;

    UserRole(String role) {
        this.authority = role;
    }
}
