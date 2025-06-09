package com.daall.howtoeat.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ScrollResponseDto<T> {
    private List<T> content;
    private boolean hasNext;
}
