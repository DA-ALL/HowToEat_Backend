package com.daall.howtoeat.common;

import com.daall.howtoeat.common.enums.SuccessType;
import lombok.Getter;

@Getter
public class ResponseDataDto<T> {
    private int status;
    private String message;
    private T data;

    public ResponseDataDto(SuccessType successType, T data) {
        this.status = successType.getHttpStatus().value();
        this.message = successType.getMessage();
        this.data = data;
    }
}
