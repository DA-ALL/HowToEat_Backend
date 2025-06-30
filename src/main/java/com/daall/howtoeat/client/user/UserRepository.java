package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryQuery {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<User> findAllByUserRole(UserRole userRole, Pageable pageable);

    boolean existsByEmailAndIdNot(String email, Long id);

    Page<User> findByNameContainingAndUserRoleIn(String name, List<UserRole> userRoles, Pageable pageable);

    long countByUserRoleIn(List<UserRole> roles);
    long countByUserRoleInAndCreatedAtBetween(List<UserRole> roles, LocalDateTime start, LocalDateTime end);
    long countByUserRoleInAndGender(List<UserRole> roles, Gender gender);
    long countByUserRoleInAndIsNextGymTrue(List<UserRole> roles);
}
