package com.daall.howtoeat.admin.user;

import com.daall.howtoeat.admin.user.dto.AdminUserDetailResponseDto;
import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.admin.user.dto.AdminUserResponseDto;
import com.daall.howtoeat.client.user.UserTargetService;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryService;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final UserTargetService userTargetService;
    private final UserDailySummaryService userDailySummaryService;

    public Page<AdminUserResponseDto> getUsers(int page, int size, String name, String orderBy, Boolean isNextGym, UserRole userRole, Boolean isAddPtMember) {
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

    public AdminUserResponseDto getUser(Long userId) {
        User user = getUserById(userId);

        return new AdminUserResponseDto(user);
    }

    public AdminUserDetailResponseDto getUserDetail(Long userId) {
        User user = this.getUserById(userId);
        UserTarget userTarget = userTargetService.getLatestTargetBeforeOrOn(user, LocalDate.now());
        int streakDays = userDailySummaryService.getStreakDays(user);

        return new AdminUserDetailResponseDto(user,userTarget, streakDays);
    }
}
