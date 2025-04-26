package com.macadev.advet.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistsException extends AppException {
    private final String resourceType;
    private final String fieldName;
    private final transient Object fieldValue;

    public ResourceAlreadyExistsException(String resourceType, String fieldName, Object fieldValue) {
        super(String.format("%s %s: '%s' already exists", resourceType, fieldName, fieldValue));
        this.resourceType = resourceType;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
