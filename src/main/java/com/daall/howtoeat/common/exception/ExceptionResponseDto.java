package com.daall.howtoeat.common.exception;

import com.daall.howtoeat.common.enums.ErrorType;
import lombok.Getter;

@Getter
public class ExceptionResponseDto {

    private String result;
    private ErrorType errorType;
    private String message;

    public ExceptionResponseDto(ErrorType errorType) {
        this.result = "ERROR";
        this.errorType = errorType;
        this.message = errorType.getMessage();
    }

    public ExceptionResponseDto(String message) {
        this.result = "ERROR";
        this.message = message;
    }
}
