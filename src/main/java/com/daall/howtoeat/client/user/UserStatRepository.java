package com.daall.howtoeat.client.user;

import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatRepository extends JpaRepository<UserStat, Long>  {
}
