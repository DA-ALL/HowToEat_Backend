package com.daall.howtoeat.domain.pt;

import com.daall.howtoeat.admin.gym.dto.GymRequestDto;
import com.daall.howtoeat.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "gyms")
public class Gym extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Gym(GymRequestDto requestDto){
        this.name = requestDto.getName();
    }

    public void updateGym(GymRequestDto requestDto) {
        this.name = requestDto.getName();
    }
}
