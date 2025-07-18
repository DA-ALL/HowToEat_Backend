package com.daall.howtoeat.domain.pt;

import com.daall.howtoeat.admin.trainer.dto.TrainerRequestDto;
import com.daall.howtoeat.admin.trainer.dto.TrainerResponseDto;
import com.daall.howtoeat.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Setter
    private String imageUrl;

    public Trainer(Gym gym, String name) {
        this.gym = gym;
        this.name = name;
    }

    public void update(Gym gym, String name) {
        this.gym = gym;
        this.name = name;
    }
}
