package com.daall.howtoeat.client.user;

import com.daall.howtoeat.client.user.dto.SignupRequestDto;
import com.daall.howtoeat.client.user.dto.UserInfoBasicResponseDto;
import com.daall.howtoeat.client.user.dto.UserSignupDateResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessageDto> signup(@RequestBody @Valid SignupRequestDto requestDto, HttpServletResponse response) {
        User user = userService.signup(requestDto);

        authService.issueTokens(user, response);
        SuccessType successType = SuccessType.CREATE_USER_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }


    /**
     * 현재 로그인한 유저의 회원가입 날짜를 조회합니다.
     *
     * @param userDetails 현재 로그인한 유저의 인증 정보
     * @return ResponseEntity<ResponseDataDto<UserSignupDateResponseDto>> - 회원가입 날짜와 성공 응답을 포함한 ResponseEntity
     *
     */
    @GetMapping("/users/signup-date")
    public ResponseEntity<ResponseDataDto<UserSignupDateResponseDto>> getUserSignupDate(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User loginUser = userDetails.getUser();
        UserSignupDateResponseDto responseDto = userService.getUserSignupDate(loginUser);
        SuccessType successType = SuccessType.GET_USER_SIGNUP_DATE_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }

    /**
     * 현재 로그인한 유저의 회원가입 날짜를 조회합니다.
     *
     * @param userDetails 현재 로그인한 유저의 인증 정보
     * @return ResponseEntity<ResponseDataDto<UserSignupDateResponseDto>> - 회원가입 날짜와 성공 응답을 포함한 ResponseEntity
     *
     */
    @GetMapping("/users/basic-info")
    public ResponseEntity<ResponseDataDto<UserInfoBasicResponseDto>> getUserBasicInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User loginUser = userDetails.getUser();
        UserInfoBasicResponseDto responseDto = userService.getUserBasicInfo(loginUser);
        SuccessType successType = SuccessType.GET_USER_BASIC_INFO_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }

}
