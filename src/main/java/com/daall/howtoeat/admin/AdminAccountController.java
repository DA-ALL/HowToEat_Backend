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

    /**
     * 관리자 계정 전체 조회
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return
     */
    @GetMapping("/accounts")
    private ResponseEntity<PageResponseDto<AdminAccountResponseDto>> getAdminAccounts (
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size
            ){
        PageResponseDto<AdminAccountResponseDto> adminAccounts = adminAccountService.getAdminAccounts(page - 1, size);

        return ResponseEntity.ok(adminAccounts);
    }

    @GetMapping("/accounts/{accountId}")
    private ResponseEntity<ResponseDataDto<AdminAccountResponseDto>> getAdminAccounts (@PathVariable Long accountId){
        ResponseDataDto<AdminAccountResponseDto> adminAccount = adminAccountService.getAdminAccount(accountId);

        return ResponseEntity.ok(adminAccount);
    }

    /**
     * 관리자 계정 추가
     * @param userDetails
     * @param requestDto
     * @return
     */
    @PostMapping("/accounts")
    private ResponseEntity<ResponseMessageDto> createAdminAccount (
            UserDetailsImpl userDetails,
            @Valid @RequestBody AdminAccountRequestDto requestDto){
        adminAccountService.createAdminAccount(userDetails.getUser(), requestDto);

        return ResponseEntity.ok(new ResponseMessageDto(SuccessType.ADMIN_ACCOUNT_CREATE_SUCCESS));
    }
}
