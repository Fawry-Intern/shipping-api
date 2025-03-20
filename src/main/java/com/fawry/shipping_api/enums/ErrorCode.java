package com.fawry.shipping_api.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    RESOURCE_ALREADY_EXISTS(HttpStatus.CONFLICT, "Resource already exists"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation failed");

    private HttpStatus httpStatus;
    private String defaultMessage;
    ErrorCode(HttpStatus status, String defaultMessage){
        this.httpStatus = status;
        this.defaultMessage = defaultMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    public String getDefaultMessage() {
        return defaultMessage;
    }
}
