package com.daall.howtoeat.admin;

import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAccountController {
    private final AdminAccountService adminAccountService;

    @PostMapping("/accounts")
    private ResponseEntity<ResponseMessageDto> createAdminAccount(
            UserDetailsImpl userDetails,
            @Valid @RequestBody AdminAccountRequestDto requestDto){
        adminAccountService.createAdminAccount(userDetails.getUser(), requestDto);

        return ResponseEntity.ok(new ResponseMessageDto(SuccessType.ADMIN_ACCOUNT_CREATE_SUCCESS));
    }
}
