package com.daall.howtoeat.client.usertarget;

import com.daall.howtoeat.client.user.UserStatRepository;
import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.client.userdailysummary.UserDailySummaryRepository;
import com.daall.howtoeat.client.userstat.dto.UserHeightRequestDto;
import com.daall.howtoeat.client.userstat.dto.UserWeightRequestDto;
import com.daall.howtoeat.client.usertarget.dto.TargetKcalsResponseDto;
import com.daall.howtoeat.client.usertarget.dto.UserInfoDetailRequestDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.enums.Gender;
import com.daall.howtoeat.common.enums.UserActivityLevel;
import com.daall.howtoeat.common.enums.UserGoal;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserDailySummary;
import com.daall.howtoeat.domain.user.UserStat;
import com.daall.howtoeat.domain.user.UserTarget;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTargetService {
    private final UserTargetRepository userTargetRepository;
    private final UserStatRepository userStatRepository;
    private final UserDailySummaryRepository userDailySummaryRepository;

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

        double targetKcal = 0.0;
        //근육 증량의 경우 1.07을 곱하고 그 이외는 설정에 맞게 kcal 산정하기
        if (userBodyInfo.getGoal() == UserGoal.GAIN_MUSCLE) {
            targetKcal = round(tdee * 1.07);
        } else {
            targetKcal = round(tdee + userBodyInfo.getGoal().getKcalOffset());
        }
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
     * 몸무게 업데이트로 인한 새로운 타겟 생성
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
    public void updateTarget(User loginUser, UserInfoDetailRequestDto requestDto) {
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        UserTarget userTarget = getLatestTargetBeforeOrOn(loginUser, LocalDate.now());

        UserStat userStat = userStatRepository.findTopByUserOrderByIdDesc(loginUser).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_STAT));

        UserDailySummary summary = userDailySummaryRepository.findByUserAndCreatedAtBetween(loginUser, start, end).orElse(null);

        UserBodyInfo userBodyInfo = new UserBodyInfo(loginUser.getGender(), userStat.getHeight(), userStat.getWeight(), loginUser.getBirth(), requestDto.getUserActivityLevel(), requestDto.getUserGoal());

        UserTarget generatedTarget = generateUserTarget(userBodyInfo, loginUser);

        if(userTarget.getCreatedAt().toLocalDate().equals(today)) {
            userTarget.updateTarget(generatedTarget);
        } else {
            UserTarget newTarget = userTargetRepository.save(generatedTarget);
            if(summary != null) {
                summary.updateSummaryTarget(newTarget);
            }
        }
    }

    /**
     * Report page 차트를 위한 Target 조회
     * @param loginUser
     * @param startDate
     * @param endDate
     * @return
     */
    public List<TargetKcalsResponseDto> getTargetKcals(User loginUser, LocalDate startDate, LocalDate endDate) {
        // 시작날짜부터 끝날짜까지 안에있는 타겟을 조회
        // 없으면 가장 최근꺼 가져와서 start부터 end까지 채움
        // 8월 1일부터 8월 30일이라면,
        // 3일, 10일에 Target이 있다고 가정하면
        // 8월 3일 이전에 가장 최근데이터가 있는지 확인,
        // 있으면 그 데이터의 kcal값을 1,2일에 넣어줘야함. 없으면 3일의 값을 넣어줌
        // 이후 3일부터 10일까지는 3일의 값을 넣어줌
        // 10일부터 30일까지는 10일의 값을 넣어줘야함.

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate/endDate must not be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be <= endDate");
        }

        LocalDateTime rangeStart = startDate.atStartOfDay();
        LocalDateTime rangeEnd = endDate.plusDays(1).atStartOfDay().minusNanos(1);

        // 1) 구간 내 타겟 조회 (createdAt 기준 오름차순)
        List<UserTarget> inRange = userTargetRepository
                .findByUserAndCreatedAtBetweenOrderByCreatedAtAsc(loginUser, rangeStart, rangeEnd);

        // 2) 구간 전체에 적용할 타겟이 전혀 없다면 → endDate 기준 가장 최근 타겟으로 전체 채움
        if (inRange.isEmpty()) {
            Optional<UserTarget> latest = userTargetRepository
                    .findTopByUserAndCreatedAtLessThanEqualOrderByCreatedAtDesc(loginUser, rangeEnd);

            if (latest.isEmpty()) {
                return Collections.emptyList(); // 아예 타겟이 없을 경우
            }

            List<TargetKcalsResponseDto> all = new ArrayList<>();
            for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
                all.add(new TargetKcalsResponseDto(latest.get(), d));
            }
            return all;
        }

        // 3) 첫 타겟 이전 날짜 메우기
        List<TargetKcalsResponseDto> result = new ArrayList<>();
        UserTarget first = inRange.get(0);

        Optional<UserTarget> priorOpt = userTargetRepository
                .findTopByUserAndCreatedAtLessThanOrderByCreatedAtDesc(loginUser, first.getCreatedAt());

        UserTarget carryForBeforeFirst = priorOpt.orElse(first);

        LocalDate cursor = startDate;
        LocalDate beforeFirstEnd = first.getCreatedAt().toLocalDate().minusDays(1);

        // 시작일자가 inRange 첫번째 보다 과거이면
        if (!cursor.isAfter(beforeFirstEnd)) {
            LocalDate segEnd = beforeFirstEnd.isAfter(endDate) ? endDate : beforeFirstEnd;
            for (LocalDate d = cursor; !d.isAfter(segEnd); d = d.plusDays(1)) {
                result.add(new TargetKcalsResponseDto(carryForBeforeFirst, d));
            }
            cursor = segEnd.plusDays(1);
        }

        // 4) inRange 타겟들로 세그먼트 채우기
        for (int i = 0; i < inRange.size(); i++) {
            UserTarget curTarget = inRange.get(i);

            LocalDate segStart = curTarget.getCreatedAt().toLocalDate();
            if (segStart.isBefore(cursor)) segStart = cursor;

            LocalDate segEnd = (i + 1 < inRange.size())
                    ? inRange.get(i + 1).getCreatedAt().toLocalDate().minusDays(1)
                    : endDate;

            if (segStart.isAfter(endDate)) break;
            if (segEnd.isBefore(segStart)) continue;

            for (LocalDate d = segStart; !d.isAfter(segEnd); d = d.plusDays(1)) {
                result.add(new TargetKcalsResponseDto(curTarget, d));
            }
            cursor = segEnd.plusDays(1);
            if (cursor.isAfter(endDate)) break;
        }

        // 5) 남은 날짜가 있으면 마지막 타겟으로 채우기
        if (!cursor.isAfter(endDate)) {
            UserTarget last = inRange.get(inRange.size() - 1);
            for (LocalDate d = cursor; !d.isAfter(endDate); d = d.plusDays(1)) {
                result.add(new TargetKcalsResponseDto(last, d));
            }
        }

        return result;
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
