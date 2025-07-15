package com.daall.howtoeat.client.notice.dto;


import com.daall.howtoeat.common.enums.NoticeType;
import com.daall.howtoeat.domain.notice.Notice;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserNoticeDetailResponseDto {
    private String title;
    private String content;
    private NoticeType type;
    private LocalDate modifiedAt;

    public UserNoticeDetailResponseDto(Notice notice) {
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.type = notice.getNoticeType();
        this.modifiedAt = notice.getModifiedAt().toLocalDate();
    }
}
