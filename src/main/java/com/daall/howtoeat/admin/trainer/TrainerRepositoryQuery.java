package com.daall.howtoeat.admin.trainer;

import com.daall.howtoeat.domain.pt.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainerRepositoryQuery {
    Page<Trainer> searchTrainers(String name, Long gymId, Pageable pageable);
}
