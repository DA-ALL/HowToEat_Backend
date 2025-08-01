package com.daall.howtoeat.client.user;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatRepository extends JpaRepository<UserStat, Long>  {
    //회원탈퇴 시 연관관계 끊기
    void deleteAllByUser(User user);

    Optional<UserStat> findTopByUserOrderByIdDesc(User user);

    List<UserStat> findAllByUserOrderByWeightRecordedAtAsc(User loginUser);
}
