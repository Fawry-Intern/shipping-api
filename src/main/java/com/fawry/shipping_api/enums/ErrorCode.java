package com.fawry.shipping_api.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ENTITY_NOT_FOUND("404", "Entity not found"),
    DUPLICATE_RESOURCE("409", "Duplicate resource"),
    ILLEGAL_ACTION("400", "Illegal action"),
    MISSING_PARAMETER("400", "Missing request parameter"),
    TYPE_MISMATCH("400", "Method argument type mismatch"),
    VALIDATION_ERROR("400", "Validation error");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
