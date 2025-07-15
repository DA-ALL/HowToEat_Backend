package com.daall.howtoeat.common.enums;

import lombok.Getter;

@Getter
public enum UserActivityLevel {
    VERY_HIGH(1.9),
    HIGH(1.725),
    NORMAL(1.55),
    LOW(1.375),
    VERY_LOW(1.2);

    private final Double activityFactor;

    UserActivityLevel(Double amount) {
        this.activityFactor = amount;
    }
}
