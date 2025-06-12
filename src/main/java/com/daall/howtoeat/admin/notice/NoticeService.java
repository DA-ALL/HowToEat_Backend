package com.daall.howtoeat.admin.notice;

import com.daall.howtoeat.admin.notice.dto.NoticeResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.notice.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public Page<NoticeResponseDto> getNotices(Integer page, Integer size, String title) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Notice> notices;

        if(title != null && title.trim().isEmpty()){
            notices = noticeRepository.findByTitleContaining(title, pageable);
        } else {
            notices = noticeRepository.findAll(pageable);
        }

        return notices.map(NoticeResponseDto::new);
    }


    public NoticeResponseDto getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_NOTICE)
        );

        return new NoticeResponseDto(notice);
    }
}
