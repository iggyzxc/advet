package com.macadev.advet.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends AppException {
    private final String resourceType;
    private final transient Object identifier; // Use Object for flexibility (Long ID, String number, etc.)

    public ResourceNotFoundException(String resourceType, Object identifier) {
        super(String.format("%s '%s' cannot be found", resourceType, identifier));
        this.resourceType = resourceType;
        this.identifier = identifier;
    }

    public ResourceNotFoundException(String message) {
        super(message);
        this.resourceType = "Resource"; // Default value
        this.identifier = "N/A";
    }
}
