package com.daall.howtoeat.client.usertarget;

import com.daall.howtoeat.client.usertarget.dto.TargetKcalsResponseDto;
import com.daall.howtoeat.client.usertarget.dto.UserInfoDetailRequestDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserTargetController {
    private final UserTargetService userTargetService;

    @PatchMapping("/users/detail-info")
    public ResponseEntity<ResponseMessageDto> updateWeight(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UserInfoDetailRequestDto requestDto
    ) {
        User loginUser = userDetails.getUser();
        userTargetService.updateTarget(loginUser, requestDto);

        SuccessType successType = SuccessType.UPDATE_USER_DETAIL_INFO_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    @GetMapping("/targets/kcals")
    public ResponseEntity<ResponseDataDto<List<TargetKcalsResponseDto>>> get30DaysTargetKcals(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ){
        List<TargetKcalsResponseDto> result = userTargetService.getTargetKcals(userDetails.getUser(), startDate, endDate);

        SuccessType successType = SuccessType.GET_USER_TARGET_KCAL_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, result));
    }
}
