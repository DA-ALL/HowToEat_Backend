package com.daall.howtoeat.admin.user;

import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.admin.user.dto.AdminUserResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;

    public Page<AdminUserResponseDto> getUsersByName(int page, int size, String name, String orderBy, Boolean isNextGym, UserRole userRole, Boolean isAddPtMember) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;

        System.out.println(", userRole: " + userRole + ", isNextGym: " + isNextGym + ", isAddPtMember: " + isAddPtMember);

        if(isAddPtMember) {
            users = userRepository.findByNameContaining(name, pageable);
        } else {
            users =  userRepository.findUsersByConditions(name, isNextGym, userRole, orderBy, pageable);
        }

        return users.map(AdminUserResponseDto::new);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER)
        );
    }
}
