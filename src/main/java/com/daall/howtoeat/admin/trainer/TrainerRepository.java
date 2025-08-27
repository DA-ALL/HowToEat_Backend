package com.daall.howtoeat.admin.trainer;

import com.daall.howtoeat.domain.pt.Gym;
import com.daall.howtoeat.domain.pt.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long>, TrainerRepositoryQuery{
    void deleteAllByGym(Gym gym);

    List<Trainer> findAllByGym(Gym gym);
}
