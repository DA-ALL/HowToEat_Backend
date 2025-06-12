package com.daall.howtoeat.admin.notice;

import com.daall.howtoeat.admin.notice.dto.NoticeResponseDto;
import com.daall.howtoeat.common.PageResponseDto;
import com.daall.howtoeat.common.ResponseDataDto;
import com.daall.howtoeat.common.enums.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/notices")
public class NoticeController {
    private final NoticeService noticeService;

    /**
     * 공지사항 전체 조회
     * @param page 페이지
     * @param size 페이지 크기
     * @param title 검색 타이틀
     * @return 공지사항 리스트
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<NoticeResponseDto>> getNotices (
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "title", required = false) String title
    ){
        Page<NoticeResponseDto> notices = noticeService.getNotices(page, size, title);
        SuccessType successType = SuccessType.GET_ALL_NOTICES_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new PageResponseDto<>(successType, notices));
    }

    /**
     * 공지사항 단일 조회
     * @param noticeId 공지사항 아이디
     * @return 공지사항
     */
    @GetMapping("/{noticeId}")
    public ResponseEntity<ResponseDataDto<NoticeResponseDto>> getNotice(@PathVariable Long noticeId){
        NoticeResponseDto responseDto = noticeService.getNotice(noticeId);

        SuccessType successType = SuccessType.GET_NOTICE_DETAIL_SUCCESS;

        return ResponseEntity.status(successType.getHttpStatus()).body(new ResponseDataDto<>(successType, responseDto));
    }


}
