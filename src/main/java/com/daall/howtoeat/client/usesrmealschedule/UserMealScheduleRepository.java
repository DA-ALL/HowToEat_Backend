package com.daall.howtoeat.client.usesrmealschedule;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserMealSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMealScheduleRepository extends JpaRepository<UserMealSchedule, Long> {
    //회원탈퇴 시 연관관계 끊기
    void deleteAllByUser(User user);
}
