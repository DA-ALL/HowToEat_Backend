package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepositoryQuery {
    Page<User> findUsersByConditions(String name, Boolean isNextGym, String orderBy, Pageable pageable);
}
