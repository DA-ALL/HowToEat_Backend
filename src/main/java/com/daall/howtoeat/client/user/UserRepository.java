package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<User> findAllByUserRole(UserRole userRole, Pageable pageable);
}
