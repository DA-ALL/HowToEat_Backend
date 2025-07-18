package com.daall.howtoeat.client.userstat;

import com.daall.howtoeat.client.user.UserStatRepository;
import com.daall.howtoeat.client.userstat.dto.UserWeightResponseDto;
import com.daall.howtoeat.client.usertarget.UserTargetService;
import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.client.userstat.dto.UserHeightRequestDto;
import com.daall.howtoeat.client.userstat.dto.UserWeightRequestDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStat;
import com.daall.howtoeat.domain.user.UserTarget;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStatService {
    private final UserStatRepository userStatRepository;
    private final UserTargetService userTargetService;

    /**
     * 첫 회원가입 시, 유저 스탯 생성
     *
     * @param requestDto 회원가입 폼으로 제출 정보
     * @param user 현재 유저 정보
     */
    public void createStat(SignupRequestDto requestDto, User user) {
        UserStat userStats = new UserStat(user, requestDto);
        userStatRepository.save(userStats);
    }


    /**
     * 키 업데이트
     * @param loginUser 회원가입 폼으로 제출 정보
     * @param requestDto 현재 유저 정보
     */
    @Transactional
    public void updateHeight(User loginUser, UserHeightRequestDto requestDto) {
        LocalDate today = LocalDate.now();

        //가장 최근 UserStat 가져오기
        UserStat userStat = userStatRepository.findTopByUserOrderByIdDesc(loginUser).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_STAT));

        //만약 현재와 동일한 키를 보냈을 경우 -> 예외처리
        if (requestDto.getHeight() == userStat.getHeight()) {
            throw new CustomException(ErrorType.SAME_AS_CURRENT_HEIGHT);
        }

        //가장 최신의 userTarget가져오기
        UserTarget userTarget = userTargetService.getLatestTargetBeforeOrOn(loginUser, today);

        //타겟 업데이트
        userTargetService.updateTargetByHeight(loginUser, userStat, userTarget, requestDto);

        //키 업데이트
        userStat.updateHeight(requestDto);
    }


    /**
     * 몸무게 업데이트
     *
     * @param loginUser 회원가입 폼으로 제출 정보
     * @param requestDto 현재 유저 정보
     */
    @Transactional
    public void updateWeight(User loginUser, UserWeightRequestDto requestDto) {
        LocalDate today = LocalDate.now();

        //가장 최근 UserStat 가져오기
        UserStat userStat = userStatRepository.findTopByUserOrderByIdDesc(loginUser).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_STAT));

        //만약 현재와 동일한 몸무게를 보냈을 경우 -> 예외처리
        if (requestDto.getWeight() == userStat.getWeight()) {
            throw new CustomException(ErrorType.SAME_AS_CURRENT_WEIGHT);
        }

        //가장 최신의 userTarget가져오기
        UserTarget userTarget = userTargetService.getLatestTargetBeforeOrOn(loginUser, today);

        //타겟 업데이트
        userTargetService.updateTargetByWeight(loginUser, userStat, userTarget, requestDto);

        //유저 스탯의 가장 최근 수정한 날짜가 오늘과 같으면 몸무게만 업데이트
        if(userStat.getWeightRecordedAt().equals(today)) {
            userStat.updateWeight(requestDto);
        } else {
            UserStat newUserStat = new UserStat(loginUser, userStat, requestDto);
            userStatRepository.save(newUserStat);
        }
    }

    //최신 유저 스탯 가져오기
    public UserStat getRecentUserStat(User loginUser) {
        return userStatRepository.findTopByUserOrderByIdDesc(loginUser).orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_USER_STAT));
    }


    public List<UserWeightResponseDto> getUserStatsWeight(User loginUser) {
        List<UserStat> userStats = userStatRepository.findAllByUserOrderByWeightRecordedAtAsc(loginUser);

        return userStats.stream().map(UserWeightResponseDto::new).toList();
    }
}
