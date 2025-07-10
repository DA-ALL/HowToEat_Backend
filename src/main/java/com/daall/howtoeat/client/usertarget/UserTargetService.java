package com.daall.howtoeat.client.usertarget;

import com.daall.howtoeat.client.user.UserStatRepository;
import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.client.userstat.dto.UserHeightRequestDto;
import com.daall.howtoeat.client.userstat.dto.UserWeightRequestDto;
import com.daall.howtoeat.client.usertarget.dto.UserInfoDetailRequestDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.UserActivityLevel;
import com.daall.howtoeat.common.enums.UserGoal;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStat;
import com.daall.howtoeat.domain.user.UserTarget;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserTargetService {
    private final UserTargetRepository userTargetRepository;
    private final UserStatRepository userStatRepository;

    /**
     * 첫 회원가입 시, 유저 타겟 macros 생성
     */
    public void createTarget(SignupRequestDto requestDto, User user) {
        UserBodyInfo userBodyInfo = new UserBodyInfo(requestDto.getGender(), requestDto.getHeight(), requestDto.getWeight(), requestDto.getBirthday(), requestDto.getActivityLevel(), requestDto.getGoal());
        generateUserTarget(userBodyInfo, user);
        userTargetRepository.save(generateUserTarget(userBodyInfo, user));
    }

    /**
     * 첫 회원가입 시, 유저 타겟 macros 생성
     * @return UserTarget
     */
    private UserTarget generateUserTarget(UserBodyInfo userBodyInfo, User user) {

        double bmr;
        if (userBodyInfo.getGender() == Gender.MALE) {
            bmr = (10 * userBodyInfo.getWeight()) + (6.25 * userBodyInfo.getHeight()) - (5 * userBodyInfo.getAge()) + 5;
        } else {
            bmr = (10 * userBodyInfo.getWeight()) + (6.25 * userBodyInfo.getHeight()) - (5 * userBodyInfo.getAge()) - 161;
        }

        double tdee = round(bmr * userBodyInfo.getActivity().getActivityFactor());
        double targetKcal = round(tdee + userBodyInfo.getGoal().getKcalOffset());
        double targetCarbo = round((targetKcal * userBodyInfo.getGoal().getCarboRatio() / 100) / 4);
        double targetProtein = round((targetKcal * userBodyInfo.getGoal().getProteinRatio() / 100) / 4);
        double targetFat = round((targetKcal * userBodyInfo.getGoal().getFatRatio() / 100) / 9);

        return new UserTarget(user, targetKcal, targetCarbo, targetProtein, targetFat, userBodyInfo.getGoal(), userBodyInfo.getActivity());
    }


    /**
     * 키 업데이트로 인한 새로운 타겟 생성
     */
    @Transactional
    public void updateTargetByHeight(User loginUser, UserStat userStat, UserTarget userTarget, UserHeightRequestDto requestDto) {
        LocalDate today = LocalDate.now();

        UserBodyInfo userBodyInfo = new UserBodyInfo(loginUser.getGender(), requestDto.getHeight(), userStat.getWeight(), loginUser.getBirth(), userTarget.getActivityLevel(), userTarget.getGoal());

        UserTarget generatedTarget = generateUserTarget(userBodyInfo, loginUser);

        if(userTarget.getCreatedAt().toLocalDate().equals(today)) {
            userTarget.updateTargetByHeightOrWeight(generatedTarget);
        } else {
            userTargetRepository.save(generatedTarget);
        }
    }


    /**
     * 키 업데이트로 인한 새로운 타겟 생성
     */
    @Transactional
    public void updateTargetByWeight(User loginUser, UserStat userStat, UserTarget userTarget, UserWeightRequestDto requestDto) {
        LocalDate today = LocalDate.now();

        UserBodyInfo userBodyInfo = new UserBodyInfo(loginUser.getGender(), userStat.getHeight(), requestDto.getWeight(), loginUser.getBirth(), userTarget.getActivityLevel(), userTarget.getGoal());

        UserTarget generatedTarget = generateUserTarget(userBodyInfo, loginUser);

        if(userTarget.getCreatedAt().toLocalDate().equals(today)) {
            userTarget.updateTargetByHeightOrWeight(generatedTarget);
        } else {
            userTargetRepository.save(generatedTarget);
        }

    }

    @Transactional
    public void updateTarget(User loginUser, UserInfoDetailRequestDto requestDto, MultipartFile profileImageFile) {
        LocalDate today = LocalDate.now();

        System.out.println(profileImageFile);
        UserTarget userTarget = getLatestTargetBeforeOrOn(loginUser, LocalDate.now());
        UserStat userStat = userStatRepository.findTopByUserOrderByIdDesc(loginUser).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_STAT));

        UserBodyInfo userBodyInfo = new UserBodyInfo(loginUser.getGender(), userStat.getHeight(), userStat.getWeight(), loginUser.getBirth(), requestDto.getUserActivityLevel(), requestDto.getUserGoal());

        UserTarget generatedTarget = generateUserTarget(userBodyInfo, loginUser);

        if(userTarget.getCreatedAt().toLocalDate().equals(today)) {
            userTarget.updateTarget(requestDto);
        } else {
            userTargetRepository.save(generatedTarget);
        }
    }


    @Getter
    public class UserBodyInfo {
        private Gender gender;
        private Double height;
        private Double weight;
        private int age;
        private UserActivityLevel activity;
        private UserGoal goal;

        public UserBodyInfo(Gender gender, Double height, Double weight, LocalDate birthday, UserActivityLevel activity, UserGoal goal) {
            this.gender = gender;
            this.height = height;
            this.weight = weight;
            this.age = calculateAge(birthday);
            this.activity = activity;
            this.goal = goal;
        }

        //나이 계산
        private int calculateAge(LocalDate birthday) {
            if (birthday == null) return 0;
            return Period.between(birthday, LocalDate.now()).getYears();
        }
    }

    /**
     * 회원의 특정 날짜 기준으로 가장 최근의 목표 정보를 조회
     *
     * @param user  조회 대상 유저
     * @param date  기준 날짜 (이 날짜 이전 또는 당일 기준으로 가장 최근 타겟 조회)
     * @return      가장 최근에 등록된 UserTarget
     * @throws      CustomException NOT_FOUND_TARGET_ON_DATE 예외 발생 가능
     */
    public UserTarget getLatestTargetBeforeOrOn(User user, LocalDate date) {
        LocalDateTime dateTime = date.atTime(LocalTime.MAX);

        return userTargetRepository.findTopByUserAndCreatedAtLessThanEqualOrderByCreatedAtDesc(user, dateTime)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_TARGET_ON_DATE));
    }

    //소수점 둘째자리 반올림
    private double round(double value) {
        return Math.round(value * 10) / 10.0;
    }

}
