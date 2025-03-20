package com.fawry.shipping_api.exception;

import com.fawry.shipping_api.enums.ErrorCode;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(String entityName, Object identifier) {
        super(String.format("%s doesn't exists with identifier: %s", entityName, identifier), ErrorCode.RESOURCE_NOT_FOUND);
    }
}