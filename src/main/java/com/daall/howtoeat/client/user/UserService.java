package com.daall.howtoeat.client.user;

import com.daall.howtoeat.admin.ptmember.PtMemberRepository;
import com.daall.howtoeat.client.consumedfood.ConsumedFoodRepository;
import com.daall.howtoeat.client.favoritefood.FavoriteFoodRepository;
import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.client.user.dto.UserInfoBasicResponseDto;
import com.daall.howtoeat.client.user.dto.UserInfoDetailResponseDto;
import com.daall.howtoeat.client.user.dto.UserSignupDateResponseDto;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryRepository;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryService;
import com.daall.howtoeat.client.userstat.UserStatService;
import com.daall.howtoeat.client.usertarget.UserTargetRepository;
import com.daall.howtoeat.client.usertarget.UserTargetService;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStat;
import com.daall.howtoeat.domain.user.UserTarget;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserTargetService userTargetService;
    private final UserStatService userStatService;
    private final UserDailySummaryService userDailySummaryService;
    private final ConsumedFoodRepository consumedFoodRepository;
    private final FavoriteFoodRepository favoriteFoodRepository;
    private final UserDailySummaryRepository userDailySummaryRepository;
    private final UserTargetRepository userTargetRepository;
    private final UserStatRepository userStatRepository;
    private final PtMemberRepository ptMemberRepository;

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
     * 유저 회원가입
     *
     * @param requestDto 유저 정보
     * @return User
     */
    @Transactional
    public User signup(SignupRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorType.ALREADY_EXISTS_EMAIL);
        }

        User user = new User(requestDto);

        User savedUser = userRepository.save(user);

        userTargetService.createTarget(requestDto, savedUser);

        userStatService.createStat(requestDto, savedUser);
        return user;
    }

    /**
     * 유저 기본정보 조회
     *
     * @param loginUser 현재 로그인한 유저
     * @return UserInfoBasicResponseDto 유저 기본 정보 값
     */
    public UserInfoBasicResponseDto getUserBasicInfo(User loginUser) {
        LocalDate dateTime = LocalDate.now();
        UserTarget userTarget = userTargetService.getLatestTargetBeforeOrOn(loginUser, dateTime);
        //연속 기록 날짜
        int streakDay = userDailySummaryService.getStreakDays(loginUser);

        return new UserInfoBasicResponseDto(loginUser, userTarget, streakDay);
    }


    /**
     * 유저 세부정보 조회
     *
     * @param loginUser 현재 로그인한 유저
     * @return UserInfoBasicResponseDto 유저 세부 정보 값
     */
    public UserInfoDetailResponseDto getUserDetailInfo(User loginUser) {
        UserTarget userTarget = userTargetService.getLatestTargetBeforeOrOn(loginUser, LocalDate.now());
        UserStat userStat = userStatService.getRecentUserStat(loginUser);

        return new UserInfoDetailResponseDto(loginUser, userTarget, userStat);
    }



    /**
     * 유저 프로필 이미지 변경
     *
     * @param loginUser 현재 로그인한 유저
     * @param profileImageFile 이미지 파일
     *
     */
    public void updateProfileImage(User loginUser, MultipartFile profileImageFile) {
        System.out.println("파일 : " + profileImageFile);
    }


    /**
     * 유저 로그아웃
     *
     * @param loginUser 유저 정보
     */
    @Transactional
    public void logout(User loginUser) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));

        if (user.getRefreshToken() == null || user.getRefreshToken().isEmpty()) {
            throw new CustomException(ErrorType.ALREADY_LOGGED_OUT);
        }
        user.deleteRefreshToken();
    }

    /**
     * 유저 회원탈퇴
     *
     * @param loginUser 유저 정보
     */
    @Transactional
    public void withdraw(User loginUser) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER));
        consumedFoodRepository.deleteAllByUser(user);
        favoriteFoodRepository.deleteAllByUser(user);
        userDailySummaryRepository.deleteAllByUser(user);
        userTargetRepository.deleteAllByUser(user);
        userStatRepository.deleteAllByUser(user);
        ptMemberRepository.deleteAllByUser(user);

        userRepository.delete(user);
    }



    public void updateRefreshToken(User user, String refreshToken) {
        user.saveRefreshToken(refreshToken);
        userRepository.save(user);
    }
}
