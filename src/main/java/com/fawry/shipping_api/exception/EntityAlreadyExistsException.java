package com.fawry.shipping_api.exception;

import com.fawry.shipping_api.enums.ErrorCode;

public class EntityAlreadyExistsException extends BaseException {
    public EntityAlreadyExistsException(String entityName, Object identifier) {
        super(String.format("%s already exists with identifier: %s", entityName, identifier), ErrorCode.RESOURCE_ALREADY_EXISTS);
    }
}
