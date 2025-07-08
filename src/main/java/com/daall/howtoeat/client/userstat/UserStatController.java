package com.daall.howtoeat.client.userstat;

import com.daall.howtoeat.client.userstat.dto.UserHeightRequestDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
