package com.fawry.shipping_api.exception;

import com.fawry.shipping_api.enums.ResourceType;
import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {

    private final ResourceType resource;

    public DuplicateResourceException(String message, ResourceType resource) {
        super(message);
        this.resource = resource;
    }
}