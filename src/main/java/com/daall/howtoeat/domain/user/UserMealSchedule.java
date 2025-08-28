package com.daall.howtoeat.domain.user;

import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_meal_schedules")
public class UserMealSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private LocalTime breakfastTime;

    @Column(nullable = false)
    private LocalTime lunchTime;

    @Column(nullable = false)
    private LocalTime dinnerTime;

    public UserMealSchedule(User user, SignupRequestDto requestDto) {
        this.user = user;
        this.breakfastTime = requestDto.getBreakfastTime();
        this.lunchTime = requestDto.getLunchTime();
        this.dinnerTime = requestDto.getDinnerTime();
    }
}
