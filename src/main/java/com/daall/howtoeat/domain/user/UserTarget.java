package com.daall.howtoeat.domain.user;

import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.common.enums.UserActivityLevel;
import com.daall.howtoeat.common.enums.UserGoal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
@Table(name = "user_targets")
public class UserTarget extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double kcal;

    @Column(nullable = false)
    private Double carbo;

    @Column(nullable = false)
    private Double protein;

    @Column(nullable = false)
    private Double fat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserGoal goal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserActivityLevel activityLevel;

    public UserTarget(SignupRequestDto signupRequestDto, User user) {
        this.user = user;
        this.kcal = kcal;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.goal = goal;
        this.activityLevel = activityLevel;
    }

    public UserTarget(User user, Double kcal, Double carbo, Double protein, Double fat,
                      UserGoal goal, UserActivityLevel activityLevel) {
        this.user = user;
        this.kcal = kcal;
        this.carbo = carbo;
        this.protein = protein;
        this.fat = fat;
        this.goal = goal;
        this.activityLevel = activityLevel;
    }
}
