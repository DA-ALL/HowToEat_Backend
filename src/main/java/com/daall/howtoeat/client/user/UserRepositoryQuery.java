package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepositoryQuery {
    Page<User> findUsersByConditions(String name, Boolean isNextGym, UserRole userRole, String orderBy, Pageable pageable);
}
