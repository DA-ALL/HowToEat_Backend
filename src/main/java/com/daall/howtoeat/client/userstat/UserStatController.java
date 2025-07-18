package com.daall.howtoeat.client.userstat;

import com.daall.howtoeat.client.userstat.dto.UserHeightRequestDto;
import com.daall.howtoeat.client.userstat.dto.UserWeightRequestDto;
import com.daall.howtoeat.client.userstat.dto.UserWeightResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserStatController {
    private final UserStatService userStatService;

    @PatchMapping("/user-info/height")
    public ResponseEntity<ResponseMessageDto> updateHeight(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserHeightRequestDto requestDto
    ) {
        User loginUser = userDetails.getUser();

        userStatService.updateHeight(loginUser, requestDto);
        SuccessType successType = SuccessType.UPDATE_USER_HEIGHT_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }


    @PatchMapping("/user-info/weight")
    public ResponseEntity<ResponseMessageDto> updateWeight(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserWeightRequestDto requestDto
    ) {
        User loginUser = userDetails.getUser();

        userStatService.updateWeight(loginUser, requestDto);
        SuccessType successType = SuccessType.UPDATE_USER_HEIGHT_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    @GetMapping("/user-stats/weight")
    public ResponseEntity<ResponseDataDto<List<UserWeightResponseDto>>> getUserStatsWeight(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User loginUser = userDetails.getUser();
        List<UserWeightResponseDto> responseDtoList = userStatService.getUserStatsWeight(loginUser);
        SuccessType successType = SuccessType.GET_USER_WEIGHT_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDtoList));
    }

}
