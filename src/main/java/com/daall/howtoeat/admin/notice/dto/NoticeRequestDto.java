package com.daall.howtoeat.admin.notice.dto;

import com.daall.howtoeat.common.enums.NoticeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class NoticeRequestDto {
    @NotBlank(message = "공지사항 제목을 작성해주세요.")
    private String title;
    @NotBlank(message = "공지사항 내용을 작성해주세요.")
    private String content;
    @NotNull(message = "공지사항 타입을 선택해주세요.")
    private NoticeType noticeType;
}
