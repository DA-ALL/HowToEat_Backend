package com.daall.howtoeat.admin.gym;

import com.daall.howtoeat.domain.pt.Gym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long> {
    boolean existsByName(String name);
    Page<Gym> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
