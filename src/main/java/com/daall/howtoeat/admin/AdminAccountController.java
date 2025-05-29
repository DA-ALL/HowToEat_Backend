package com.daall.howtoeat.admin;

import com.daall.howtoeat.admin.dto.AdminAccountRequestDto;
import com.daall.howtoeat.admin.dto.AdminAccountResponseDto;
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
     * @return PageResponseDto<AdminAccountResponseDto>
     */
    @GetMapping("/accounts")
    private ResponseEntity<PageResponseDto<AdminAccountResponseDto>> getAdminAccounts (
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size
            ){
        PageResponseDto<AdminAccountResponseDto> adminAccounts = adminAccountService.getAdminAccounts(page - 1, size);

        return ResponseEntity.ok(adminAccounts);
    }

    /**
     * 관리자 계정 단일 조회
     * @param accountId 조회할 아이디
     * @return ResponseDataDto<AdminAccountResponseDto>
     */
    @GetMapping("/accounts/{accountId}")
    private ResponseEntity<ResponseDataDto<AdminAccountResponseDto>> getAdminAccounts (@PathVariable Long accountId){
        ResponseDataDto<AdminAccountResponseDto> adminAccount = adminAccountService.getAdminAccount(accountId);

        return ResponseEntity.ok(adminAccount);
    }

    /**
     * 관리자 계정 추가
     * API 요청 보내는 User의 권한은 시큐리티에서 확인
     * @param requestDto 아이디, 비밀번호
     * @return ResponseMessageDto
     */
    @PostMapping("/accounts")
    private ResponseEntity<ResponseMessageDto> createAdminAccount (
            UserDetailsImpl userDetails,
            @Valid @RequestBody AdminAccountRequestDto requestDto){
        adminAccountService.createAdminAccount(requestDto);

        return ResponseEntity.ok(new ResponseMessageDto(SuccessType.CREATE_ADMIN_ACCOUNT_SUCCESS));
    }

    /**
     * 관리자 계정 수정
     * @param accountId 수정할 아이디
     * @param requestDto 수정할 아이디와 비밀번호
     * @return 성공 메시지
     */
    @PutMapping("/accounts/{accountId}")
    private ResponseEntity<ResponseMessageDto> updateAdminAccount(@PathVariable Long accountId, @Valid @RequestBody AdminAccountRequestDto requestDto) {
        adminAccountService.updateAdminAccount(accountId, requestDto);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessType.UPDATE_ADMIN_ACCOUNT_SUCCESS));
    }

    /**
     * 관리자 계정 삭제
     * @param accountId 삭제할 계정 아이디
     * @return 성공 메시지
     */
    @DeleteMapping("/accounts/{accountId}")
    private ResponseEntity<ResponseMessageDto> deleteAdminAccount(@PathVariable Long accountId) {
        adminAccountService.deleteAdminAccount(accountId);
        return ResponseEntity.ok(new ResponseMessageDto(SuccessType.DELETE_ADMIN_ACCOUNT_SUCCESS));
    }
}
