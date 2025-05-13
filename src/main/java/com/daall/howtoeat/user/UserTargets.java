package com.daall.howtoeat.user;

import com.daall.howtoeat.common.Timestamped;
import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.UserActivityLevel;
import com.daall.howtoeat.common.enums.UserGoal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_targets")
public class UserTargets extends Timestamped {
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

}
