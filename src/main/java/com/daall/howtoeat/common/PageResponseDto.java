package com.daall.howtoeat.common;

import com.daall.howtoeat.common.enums.SuccessType;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponseDto<T> {
    private int status;
    private String message;
    private List<T> content;
    private int page;
    private int size;
    private int totalPages;
    private long totalElements;

    public PageResponseDto(SuccessType successType, Page<T> page) {
        this.status = successType.getHttpStatus().value();
        this.message = successType.getMessage();
        this.content = page.getContent();
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
    }
}