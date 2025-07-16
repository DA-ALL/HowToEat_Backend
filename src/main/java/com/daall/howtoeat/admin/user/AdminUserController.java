package com.daall.howtoeat.admin.user;

import com.daall.howtoeat.admin.user.dto.*;
import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import com.daall.howtoeat.domain.user.User;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping("/admin/users")
    public ResponseEntity<PageResponseDto<AdminUserResponseDto>> getUsers(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "isNextGym", required = false) Boolean isNextGym,
            @RequestParam(value = "isAddPtMember", required = false) Boolean isAddPtMember
    ){
        Page<AdminUserResponseDto> users = adminUserService.getUsers(page - 1, size, name, orderBy, isNextGym, isAddPtMember);
        SuccessType successType = SuccessType.GET_ALL_USERS_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new PageResponseDto<>(successType, users));
    }

    @GetMapping("/admin/users/{userId}")
    public ResponseEntity<ResponseDataDto<AdminUserResponseDto>> getUser(@PathVariable Long userId){
        AdminUserResponseDto responseDto = adminUserService.getUser(userId);
        SuccessType successType = SuccessType.GET_USER_BASIC_INFO_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }

    @GetMapping("/admin/users/{userId}/detail")
    public ResponseEntity<ResponseDataDto<AdminUserDetailResponseDto>> getUserDetail(@PathVariable Long userId){
        AdminUserDetailResponseDto responseDto = adminUserService.getUserDetail(userId);
        SuccessType successType = SuccessType.GET_USER_DETAIL_INFO_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }

    @PatchMapping("/admin/users/{userId}/is-next-gym")
    public ResponseEntity<ResponseMessageDto> updateUserNextGymStatus(
            @PathVariable Long userId,
            @RequestBody UpdateNextGymStatusRequestDto requestDto
    ){
        adminUserService.updateUserNextGymStatus(userId, requestDto);
        SuccessType successType = SuccessType.UPDATE_USER_NEXT_GYM_STATUS_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    @PatchMapping("/admin/users/{userId}/user-role")
    public ResponseEntity<ResponseMessageDto> updateUserRole(
            @PathVariable Long userId,
            @RequestBody UpdateUserRoleRequestDto requestDto
    ){
        adminUserService.updateUserRole(userId, requestDto);
        SuccessType successType = SuccessType.UPDATE_USER_ROLE_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    @GetMapping("/admin/me")
    public ResponseEntity<ResponseDataDto<AdminMeResponseDto>> getAdminInfo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User loginUser = userDetails.getUser();
        SuccessType successType = SuccessType.GET_USER_BASIC_INFO_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, new AdminMeResponseDto(loginUser)));
    }

    @PostMapping("/admin/logout")
    public ResponseEntity<ResponseMessageDto> adminLogout(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HttpServletResponse response
    ){
        User loginUser = userDetails.getUser();
        adminUserService.logout(loginUser);
        SuccessType successType = SuccessType.LOGOUT_SUCCESS;

        // 쿠키 삭제
        ResponseCookie deleteCookie = ResponseCookie.from("RefreshToken", "")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .build();

        response.setHeader("Set-Cookie", deleteCookie.toString());

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}
