package com.daall.howtoeat.admin.ptmember;

import com.daall.howtoeat.admin.ptmember.dto.PtMemberRequestDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/pt-members")
public class PtMemberController {
    private final PtMemberService ptMemberService;

    @PostMapping
    public ResponseEntity<ResponseMessageDto> createPtMember(@RequestBody PtMemberRequestDto requestDto){
        ptMemberService.createPtMember(requestDto);
        SuccessType successType = SuccessType.CREATE_PTMEMBER_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }

    @DeleteMapping("/{ptMemberId}")
    public ResponseEntity<ResponseMessageDto> deletePtMember(@PathVariable Long ptMemberId){
        ptMemberService.deletePtMember(ptMemberId);
        SuccessType successType = SuccessType.DELETE_PTMEMBER_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseMessageDto(successType));
    }
}
