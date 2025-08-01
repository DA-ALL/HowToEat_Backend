package com.daall.howtoeat.admin.account;

import com.daall.howtoeat.admin.account.dto.AdminAccountRequestDto;
import com.daall.howtoeat.admin.account.dto.AdminAccountResponseDto;
import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        Page<AdminAccountResponseDto> adminAccounts = adminAccountService.getAdminAccounts(page - 1, size);
        SuccessType successType = SuccessType.GET_ALL_ADMIN_ACCOUNTS_SUCCESS;
        PageResponseDto<AdminAccountResponseDto> responseDto = new PageResponseDto<>(successType, adminAccounts);

        return ResponseEntity.status(successType.getHttpStatus()).body(responseDto);
    }

    /**
     * 관리자 계정 단일 조회
     * @param accountId 조회할 아이디
     * @return ResponseDataDto<AdminAccountResponseDto>
     */
    @GetMapping("/accounts/{accountId}")
    private ResponseEntity<ResponseDataDto<AdminAccountResponseDto>> getAdminAccount (@PathVariable Long accountId){
        AdminAccountResponseDto adminAccount = adminAccountService.getAdminAccount(accountId);
        SuccessType successType = SuccessType.GET_ADMIN_ACCOUNT_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, adminAccount));
    }

    /**
     * 관리자 계정 추가
     * API 요청 보내는 User의 권한은 시큐리티에서 확인
     * @param requestDto 아이디, 비밀번호
     * @return ResponseMessageDto
     */
    @PostMapping("/accounts")
    private ResponseEntity<ResponseMessageDto> createAdminAccount (@Valid @RequestBody AdminAccountRequestDto requestDto) {
        adminAccountService.createAdminAccount(requestDto);

        SuccessType successType = SuccessType.CREATE_ADMIN_ACCOUNT_SUCCESS;
        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
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
        SuccessType successType = SuccessType.UPDATE_ADMIN_ACCOUNT_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    /**
     * 관리자 계정 삭제
     * @param accountId 삭제할 계정 아이디
     * @return 성공 메시지
     */
    @DeleteMapping("/accounts/{accountId}")
    private ResponseEntity<ResponseMessageDto> deleteAdminAccount(@PathVariable Long accountId) {
        adminAccountService.deleteAdminAccount(accountId);
        SuccessType successType = SuccessType.DELETE_ADMIN_ACCOUNT_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}
