package com.daall.howtoeat.admin.notice;

import com.daall.howtoeat.admin.notice.dto.NoticeRequestDto;
import com.daall.howtoeat.admin.notice.dto.NoticeResponseDto;
import com.daall.howtoeat.common.enums.ErrorType;
import com.daall.howtoeat.common.exception.CustomException;
import com.daall.howtoeat.domain.notice.Notice;
import jakarta.transaction.Transactional;
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

    public Page<NoticeResponseDto> getNotices(Integer page, Integer size, String title, String orderBy) {
        Pageable pageable = orderBy.equals("asc") ? PageRequest.of(page, size, Sort.by("createdAt").ascending()) : PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Notice> notices;

        if(title != null && !title.trim().isEmpty()){
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

    public void createNotice(NoticeRequestDto noticeRequestDto) {
        Notice notice = new Notice(noticeRequestDto);

        noticeRepository.save(notice);
    }

    @Transactional
    public void updateNotice(Long noticeId, NoticeRequestDto requestDto) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_NOTICE)
        );

        notice.update(requestDto);
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_NOTICE)
        );

        noticeRepository.delete(notice);
    }
}
