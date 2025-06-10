package com.daall.howtoeat.domain.pt;

import com.daall.howtoeat.admin.trainer.dto.TrainerRequestDto;
import com.daall.howtoeat.admin.trainer.dto.TrainerResponseDto;
import com.daall.howtoeat.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "trainers")
public class Trainer extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", nullable = false)
    private Gym gym;

    @Column(nullable = false)
    private String name;

    @Column
    private String imageUrl;

    public Trainer(TrainerRequestDto requestDto, Gym gym) {
        this.gym = gym;
        this.name = requestDto.getName();
        this.imageUrl = requestDto.getImageUrl();
    }

    public void update(TrainerRequestDto requestDto, Gym gymEntity) {
        this.name = requestDto.getName();
        this.imageUrl = requestDto.getImageUrl();
        this.gym = gymEntity;
    }
}
