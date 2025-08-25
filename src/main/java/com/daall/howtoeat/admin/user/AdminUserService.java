package com.daall.howtoeat.admin.user;

import com.daall.howtoeat.admin.dailyreport.dto.UserStatisticsDto;
import com.daall.howtoeat.admin.consumedfood.AdminConsumedFoodService;
import com.daall.howtoeat.admin.user.dto.AdminUserDetailResponseDto;
import com.daall.howtoeat.admin.user.dto.UpdateNextGymStatusRequestDto;
import com.daall.howtoeat.admin.user.dto.UpdateUserRoleRequestDto;
import com.daall.howtoeat.client.user.UserRepository;
import com.daall.howtoeat.admin.user.dto.AdminUserResponseDto;
import com.daall.howtoeat.client.usertarget.UserTargetService;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryService;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.UserRole;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserTarget;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserRepository userRepository;
    private final UserTargetService userTargetService;
    private final UserDailySummaryService userDailySummaryService;
    private final AdminConsumedFoodService adminConsumedFoodService;

    public Page<AdminUserResponseDto> getUsers(int page, int size, String name, String orderBy, Boolean isNextGym, Boolean isAddPtMember) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users;

//        System.out.println(", userRole: " + userRole + ", isNextGym: " + isNextGym + ", isAddPtMember: " + isAddPtMember);

        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(UserRole.USER);
        userRoles.add(UserRole.SUPERUSER);

        if(isAddPtMember) {
            users = userRepository.findByNameContainingAndUserRoleIn(name, userRoles ,pageable);
        } else {
            users = userRepository.findUsersByConditions(name, isNextGym, orderBy, pageable);
        }

        List<Long> userIds = users.getContent().stream()
                .map(User::getId)
                .toList();

        Map<Long, Long> consumedFoodCountMap = adminConsumedFoodService.getConsumedFoodCountOfUsers(userIds);

        return users.map(user -> {
            Long consumedFoodCount = consumedFoodCountMap.getOrDefault(user.getId(), 0L);
            return new AdminUserResponseDto(user, consumedFoodCount);
        });
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_USER)
        );
    }

    public AdminUserResponseDto getUser(Long userId) {
        User user = this.findUserById(userId);
        Long consumedFoodCount = adminConsumedFoodService.getConsumedFoodCountOfUser(user);
        return new AdminUserResponseDto(user, consumedFoodCount);
    }

    public AdminUserDetailResponseDto getUserDetail(Long userId) {
        User user = this.findUserById(userId);
        UserTarget userTarget = userTargetService.getLatestTargetBeforeOrOn(user, LocalDate.now());
        int streakDays = userDailySummaryService.getStreakDays(user);

        return new AdminUserDetailResponseDto(user,userTarget, streakDays);
    }

    @Transactional
    public void updateUserNextGymStatus(Long userId, UpdateNextGymStatusRequestDto requestDto) {
        User user = this.findUserById(userId);
        user.updateNextGymStatus(requestDto);
    }

    @Transactional
    public void updateUserRole(Long userId, UpdateUserRoleRequestDto requestDto) {
        User user = this.findUserById(userId);
        user.updateUserRole(requestDto);
    }

    public UserStatisticsDto getUserStatistics(LocalDate today) {
        List<UserRole> userRoles = List.of(UserRole.USER, UserRole.SUPERUSER);
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        return new UserStatisticsDto(
            userRepository.countByUserRoleIn(userRoles),
            userRepository.countByUserRoleInAndCreatedAtBetween(userRoles, startOfDay, endOfDay),
            userRepository.countByUserRoleInAndGender(userRoles, Gender.MALE),
            userRepository.countByUserRoleInAndGender(userRoles, Gender.FEMALE),
            userRepository.countByUserRoleInAndIsNextGymTrue(userRoles)
        );
    }

    @Transactional
    public void logout(User loginUser) {
        loginUser.deleteRefreshToken();
    }
}
