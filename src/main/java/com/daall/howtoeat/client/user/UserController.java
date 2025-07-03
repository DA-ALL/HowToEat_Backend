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
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessageDto> logout(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HttpServletResponse response
    ) {
        User loginUser = userDetails.getUser();
        userService.logout(loginUser);
        SuccessType successType = SuccessType.LOGOUT_SUCCESS;

        // 쿠키 삭제 명령 (조건 일치 필수)
        ResponseCookie deleteCookie = ResponseCookie.from("RefreshToken", "")
                .path("/")                // 쿠키 삭제 시, 원래 설정된 path와 동일해야 브라우저가 정확히 삭제함
                .maxAge(0)  // 즉시 만료시키기 위해 maxAge를 0으로 설정 (삭제 의도)
                .httpOnly(true)           // 원래 쿠키가 HttpOnly=true였다면, 삭제할 때도 동일하게 설정해야 함
                .secure(false)            // SameSite=None일 경우 true 필요, 개발환경에서는 HTTPS가 아니므로 false
                .sameSite("Strict")       // 원래 쿠키의 SameSite 값과 일치해야 삭제됨 (Strict, Lax, None 중 동일하게)
                // [ SameSite ] = "이 쿠키는 다른 사이트에서 온 요청에도 포함시켜도 되나요?" 를 설정하는 속성
                // 1. Strict = 자기 사이트에서만 전송됨 (외부 링크나 iframe, form POST로는 쿠키 안보냄)
                // 2. Lax = 대부분 경우 전송됨, 단 GET 요청만 허용, 외부 POST/PUT에는 전송 안됨
                // 3. None = 모든 요청에 쿠키 포함됨. 반드시 Secure 설정도 함께 필요(https 만 허용)
                .build();


        response.setHeader("Set-Cookie", deleteCookie.toString());

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


    /**
     * 유저 회원탈퇴
     *
     * @param userDetails 현재 로그인한 유저의 인증 정보
     * @return ResponseEntity<ResponseDataDto<UserSignupDateResponseDto>> - 회원가입 날짜와 성공 응답을 포함한 ResponseEntity
     *
     */
    @DeleteMapping("/users/withdraw")
    public ResponseEntity<ResponseMessageDto> withdraw(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HttpServletResponse response
    ) {
        User loginUser = userDetails.getUser();
        userService.withdraw(loginUser);
        SuccessType successType = SuccessType.WITHDRAW_SUCCESS;

        // 쿠키 삭제 명령 (조건 일치 필수)
        ResponseCookie deleteCookie = ResponseCookie.from("RefreshToken", "")
                .path("/")                // 쿠키 삭제 시, 원래 설정된 path와 동일해야 브라우저가 정확히 삭제함
                .maxAge(0)  // 즉시 만료시키기 위해 maxAge를 0으로 설정 (삭제 의도)
                .httpOnly(true)           // 원래 쿠키가 HttpOnly=true였다면, 삭제할 때도 동일하게 설정해야 함
                .secure(false)            // SameSite=None일 경우 true 필요, 개발환경에서는 HTTPS가 아니므로 false
                .sameSite("Strict")       // 원래 쿠키의 SameSite 값과 일치해야 삭제됨 (Strict, Lax, None 중 동일하게)
                // [ SameSite ] = "이 쿠키는 다른 사이트에서 온 요청에도 포함시켜도 되나요?" 를 설정하는 속성
                // 1. Strict = 자기 사이트에서만 전송됨 (외부 링크나 iframe, form POST로는 쿠키 안보냄)
                // 2. Lax = 대부분 경우 전송됨, 단 GET 요청만 허용, 외부 POST/PUT에는 전송 안됨
                // 3. None = 모든 요청에 쿠키 포함됨. 반드시 Secure 설정도 함께 필요(https 만 허용)
                .build();


        response.setHeader("Set-Cookie", deleteCookie.toString());

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}
