package com.daall.howtoeat.common.exception;

import com.daall.howtoeat.common.enums.ErrorType;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private String result;
    private ErrorType errorType;

    public CustomException(ErrorType errorType) {
        this.result = "ERROR";
        this.errorType = errorType;
    }
}
