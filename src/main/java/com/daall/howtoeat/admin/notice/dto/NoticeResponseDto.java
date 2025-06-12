package com.daall.howtoeat.admin.notice.dto;

import com.daall.howtoeat.common.enums.NoticeType;
import com.daall.howtoeat.domain.notice.Notice;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NoticeResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final NoticeType noticeType;
    private final LocalDate createdAt;

    public NoticeResponseDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.noticeType = notice.getNoticeType();
        this.createdAt = notice.getCreatedAt().toLocalDate();
    }
}
