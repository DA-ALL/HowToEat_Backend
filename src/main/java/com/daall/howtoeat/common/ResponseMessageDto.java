package com.daall.howtoeat.common;

import com.daall.howtoeat.common.enums.SuccessType;
import lombok.Getter;

@Getter
public class ResponseMessageDto {
    private int status;
    private String message;

    public ResponseMessageDto(SuccessType successType) {
        this.status = successType.getHttpStatus().value();
        this.message = successType.getMessage();
    }
}
