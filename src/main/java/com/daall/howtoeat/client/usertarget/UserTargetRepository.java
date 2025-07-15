package com.daall.howtoeat.client.usertarget;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserTargetRepository extends JpaRepository<UserTarget, Long> {
    Optional<UserTarget> findTopByUserAndCreatedAtLessThanEqualOrderByCreatedAtDesc(User user, LocalDateTime dateTime);

    //회원탈퇴 시 연관관계 끊기
    void deleteAllByUser(User user);
}
