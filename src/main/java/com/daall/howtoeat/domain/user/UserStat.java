package com.daall.howtoeat.domain.user;

import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.client.userstat.dto.UserHeightRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_stats")
public class UserStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private LocalDate weightRecordedAt;

    @Column(nullable = false)
    private LocalDate heightRecordedAt;

    public UserStat(User user, SignupRequestDto requestDto) {
        this.user = user;
        this.height = Math.round(requestDto.getHeight() * 10) / 10.0;
        this.weight = Math.round(requestDto.getWeight() * 10) / 10.0;
        LocalDate date = LocalDate.now();
        this.weightRecordedAt = date;
        this.heightRecordedAt = date;
    }

    public void updateHeight(UserHeightRequestDto requestDto) {
        this.height = requestDto.getHeight();
        this.heightRecordedAt = LocalDate.now();
    }
}
