package com.daall.howtoeat.admin.gym;

import com.daall.howtoeat.domain.pt.Gym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GymRepository extends JpaRepository<Gym, Long>, GymRepositoryQuery{
    boolean existsByName(String name);
    Page<Gym> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Gym> findByName(String name);
}
