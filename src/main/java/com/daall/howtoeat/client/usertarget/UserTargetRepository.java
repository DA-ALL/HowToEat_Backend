package com.daall.howtoeat.client.usertarget;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTargetRepository extends JpaRepository<UserTarget, Long> {
    Optional<UserTarget> findTopByUserAndCreatedAtLessThanEqualOrderByCreatedAtDesc(User user, LocalDateTime dateTime);

    //회원탈퇴 시 연관관계 끊기
    void deleteAllByUser(User user);

    // 구간 내 타겟 (createdAt 기준)
    List<UserTarget> findByUserAndCreatedAtBetweenOrderByCreatedAtAsc(
            User user, LocalDateTime start, LocalDateTime end
    );

    // 기준일 00:00보다 이전의 가장 최근 타겟
    Optional<UserTarget> findTopByUserAndCreatedAtLessThanOrderByCreatedAtDesc(
            User user, LocalDateTime before
    );
}
