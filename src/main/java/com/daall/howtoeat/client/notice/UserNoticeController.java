package com.daall.howtoeat.client.notice;


import com.daall.howtoeat.client.notice.dto.NoticeResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/notices")
    public ResponseEntity<ResponseDataDto<NoticeResponseDto>> getNotices() {
        NoticeResponseDto responseDto = noticeService.getNotices();
        SuccessType successType = SuccessType.GET_ALL_NOTICES_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }
}
