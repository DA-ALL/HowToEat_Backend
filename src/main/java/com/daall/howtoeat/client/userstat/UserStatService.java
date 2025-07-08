package com.daall.howtoeat.client.userstat;

import com.daall.howtoeat.client.user.UserStatRepository;
import com.daall.howtoeat.client.user.UserTargetService;
import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.client.userstat.dto.UserHeightRequestDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.user.User;
import com.daall.howtoeat.domain.user.UserStat;
import com.daall.howtoeat.domain.user.UserTarget;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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


}
