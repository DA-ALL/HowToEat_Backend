package com.daall.howtoeat.common.enums;

import lombok.Getter;

@Getter
public enum UserGoal {
    LOSE_WEIGHT(-400),
    MAINTAIN_WEIGHT(0),
    GAIN_WEIGHT(500),
    GAIN_MUSCLE(-200);

    private final Integer kcalOffset;

    UserGoal(Integer amount) {
        this.kcalOffset = amount;
    }
}
