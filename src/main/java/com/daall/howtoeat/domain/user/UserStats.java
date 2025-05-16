package com.daall.howtoeat.domain.user;

import com.daall.howtoeat.client.user.SignupRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_stats")
public class UserStats {
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

    public UserStats(User user, SignupRequestDto requestDto) {
        this.user = user;
        this.weight = requestDto.getWeight();
        this.height = requestDto.getHeight();
        LocalDate date = LocalDate.now();
        this.weightRecordedAt = date;
        this.heightRecordedAt = date;
    }
}
