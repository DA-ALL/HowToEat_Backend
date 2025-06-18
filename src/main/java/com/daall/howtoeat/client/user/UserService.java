package com.daall.howtoeat.client.user;

import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.client.user.dto.UserInfoBasicResponseDto;
import com.daall.howtoeat.client.user.dto.UserSignupDateResponseDto;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryService;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import com.daall.howtoeat.domain.user.UserStat;
import com.daall.howtoeat.domain.user.UserTarget;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserTargetService userTargetService;
    private final UserDailySummaryService userDailySummaryService;

    /**
     * 유저 회원가입
     *
     * @param requestDto 유저 정보
     * @return User
     */
    @Transactional
    public User signup(SignupRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = new User(requestDto);

        User savedUser = userRepository.save(user);
        System.out.println(savedUser.getId() + " 유저 테스트입니다. ");

        userTargetService.createTarget(requestDto, savedUser);

        UserStat userStats = new UserStat(savedUser, requestDto);
        return user;
    }

    /**
     * 유저 회원가입 날짜 조회
     *
     * @param loginUser 현재 로그인한 유저
     * @return UserSignupDateResponseDto 유저 날짜 값
     */
    public UserSignupDateResponseDto getUserSignupDate(User loginUser) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));

        return new UserSignupDateResponseDto(user);
    }

    /**
     * 유저 기본정보 조회
     *
     * @param loginUser 현재 로그인한 유저
     * @return UserInfoBasicResponseDto 유저 기본 정보 값
     */
    public UserInfoBasicResponseDto getUserBasicInfo(User loginUser) {
        LocalDate dateTime = loginUser.getCreatedAt().toLocalDate();
        UserTarget userTarget = userTargetService.getLatestTargetBeforeOrOn(loginUser, dateTime);
        //연속 기록 날짜
        int streakDay = userDailySummaryService.getStreakDays(loginUser);

        return new UserInfoBasicResponseDto(loginUser, userTarget, streakDay);
    }

    public void updateRefreshToken(User user, String refreshToken) {
        user.saveRefreshToken(refreshToken);
        userRepository.save(user);
    }
}
