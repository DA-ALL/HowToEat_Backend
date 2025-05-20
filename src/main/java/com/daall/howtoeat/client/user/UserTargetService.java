package com.daall.howtoeat.client.user;

import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.UserActivityLevel;
import com.daall.howtoeat.common.enums.UserGoal;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class UserTargetService {
    private final UserTargetRepository userTargetRepository;

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

        double tdee = bmr * userBodyInfo.getActivity().getActivityFactor();
        double targetKcal = tdee + userBodyInfo.getGoal().getKcalOffset();
        double targetCarbo = (targetKcal * userBodyInfo.getGoal().getCarboRatio() / 100) / 4;
        double targetProtein = (targetKcal * userBodyInfo.getGoal().getProteinRatio() / 100) / 4;
        double targetFat = (targetKcal * userBodyInfo.getGoal().getFatRatio() / 100) / 9;

        return new UserTarget(user, targetKcal, targetCarbo, targetProtein, targetFat, userBodyInfo.getGoal(), userBodyInfo.getActivity());
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
}
