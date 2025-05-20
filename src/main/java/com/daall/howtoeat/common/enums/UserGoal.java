package com.daall.howtoeat.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserGoal {
    LOSE_WEIGHT(-400, 40, 35, 25),
    MAINTAIN_WEIGHT(0, 45, 25, 30),
    GAIN_WEIGHT(500, 40, 20, 35),
    GAIN_MUSCLE(-200, 40, 30, 30);

    private final Integer kcalOffset;
    private final Integer carboRatio;
    private final Integer proteinRatio;
    private final Integer fatRatio;

//    UserGoal(Integer kcalOffset, In) {
//        this.kcalOffset = kcalOffset;
//    }
}
