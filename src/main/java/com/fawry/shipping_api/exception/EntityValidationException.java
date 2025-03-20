package com.fawry.shipping_api.exception;

import com.fawry.shipping_api.enums.ErrorCode;

public class EntityValidationException extends BaseException {
    public EntityValidationException(String entityName, Object identifier, String message) {
        super(String.format("Validation failed for %s with identifier %s: %s", entityName, identifier, message), ErrorCode.VALIDATION_ERROR);
    }
}
