package com.daall.howtoeat.admin.user;

import com.daall.howtoeat.admin.user.dto.AdminUserResponseDto;
import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping("/admin/users")
    public ResponseEntity<PageResponseDto<AdminUserResponseDto>> getUsersByName(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "name", required = false) String name
    ){
        Page<AdminUserResponseDto> users = adminUserService.getUsersByName(page - 1, size, name);
        SuccessType successType = SuccessType.GET_ALL_USERS_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new PageResponseDto<>(successType, users));
    }
}
