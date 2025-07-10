package com.daall.howtoeat.client.usertarget;

import com.daall.howtoeat.client.usertarget.dto.UserInfoDetailRequestDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserTargetController {
    private final UserTargetService userTargetService;

    @PatchMapping("/users/detail-info")
    public ResponseEntity<ResponseMessageDto> updateWeight(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @ModelAttribute UserInfoDetailRequestDto requestDto,
            @RequestParam(value = "profileImageFile", required = false) MultipartFile foodImageFile
    ) {
        User loginUser = userDetails.getUser();
        userTargetService.updateTarget(loginUser, requestDto, foodImageFile);

        SuccessType successType = SuccessType.UPDATE_USER_DETAIL_INFO_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}
