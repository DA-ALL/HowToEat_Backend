package com.daall.howtoeat.admin.ptmember;

import com.daall.howtoeat.admin.ptmember.dto.PtMemberRequestDto;
import com.daall.howtoeat.common.ResponseMessageDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
