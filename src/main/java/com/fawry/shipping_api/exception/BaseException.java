package com.fawry.shipping_api.exception;

import com.fawry.shipping_api.enums.ErrorCode;

public class BaseException extends RuntimeException {
    private final ErrorCode errorCode;
    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
