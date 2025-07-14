package com.daall.howtoeat.client.notice.dto;

import com.daall.howtoeat.common.enums.NoticeType;
import com.daall.howtoeat.domain.notice.Notice;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Getter
public class NoticeResponseDto {
    private String title;
    private NoticeType type;
    private LocalDate modifiedAt;

    public NoticeResponseDto(Notice notice) {
        this.title = notice.getTitle();
        this.type = notice.getNoticeType();
        this.modifiedAt = notice.getModifiedAt().toLocalDate();
    }
}
