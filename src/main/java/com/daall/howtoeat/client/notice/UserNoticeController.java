package com.daall.howtoeat.client.notice;


import com.daall.howtoeat.client.notice.dto.NoticeResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserNoticeController {
    private final UserNoticeService userNoticeService;

    /**
     * 공지사항 전체 조회
     */
    @GetMapping("/notices")
    public ResponseEntity<ResponseDataDto<List<NoticeResponseDto>>> getNotices() {
        List<NoticeResponseDto> responseDtos = userNoticeService.getNotices();

        SuccessType successType = SuccessType.GET_ALL_NOTICES_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDtos));
    }
}
