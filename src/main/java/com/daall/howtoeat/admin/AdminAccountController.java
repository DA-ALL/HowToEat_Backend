package com.daall.howtoeat.admin;

import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import com.daall.howtoeat.common.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAccountController {
    private final AdminAccountService adminAccountService;

    @GetMapping("/accounts")
    private ResponseEntity<PageResponseDto<AdminAccountResponseDto>> getAdminAccounts (
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size
            ){
        PageResponseDto<AdminAccountResponseDto> adminAccounts = adminAccountService.getAdminAccounts(page - 1, size);

        return ResponseEntity.ok(adminAccounts);
    }


    @PostMapping("/accounts")
    private ResponseEntity<ResponseMessageDto> createAdminAccount (
            UserDetailsImpl userDetails,
            @Valid @RequestBody AdminAccountRequestDto requestDto){
        adminAccountService.createAdminAccount(userDetails.getUser(), requestDto);

        return ResponseEntity.ok(new ResponseMessageDto(SuccessType.ADMIN_ACCOUNT_CREATE_SUCCESS));
    }
}
