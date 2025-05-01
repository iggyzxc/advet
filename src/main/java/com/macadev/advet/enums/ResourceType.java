package com.macadev.advet.enums;

public enum ResourceType {
    USER("User"),
    APPOINTMENT("Appointment"),
    PET("Pet"),
    PHOTO("Photo"),
    REVIEW("Review");

    private final String displayName;

    ResourceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
