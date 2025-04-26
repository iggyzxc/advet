package com.macadev.advet.util;

import com.macadev.advet.enums.ResourceType;

/**
 * Utility class containing standardized feedback messages for API responses.
 * This class is final and cannot be instantiated.
 */
public final class FeedbackMessage {
    // --- Dynamic success message generators
    public static String createSuccess(ResourceType resourceType) {
        if (resourceType == null ) {
            return "Resource created successfully.";
        }
        return String.format("%s created successfully.", capitalize(resourceType.name()));
    }

    public static String updateSuccess(ResourceType resourceType) {
        if (resourceType == null) {
            return "Resource updated successfully.";
        }
        return String.format("%s updated successfully.", capitalize(resourceType.name()));
    }

    public static String foundSuccess(ResourceType resourceType) {
        if (resourceType == null) {
            return "Resource found successfully.";
        }
        return String.format("%s found successfully.", capitalize(resourceType.name()));
    }

    public static String deleteSuccess(ResourceType resourceType) {
        if (resourceType == null) {
            return "Resource deleted successfully.";
        }
        return String.format("%s deleted successfully.", capitalize(resourceType.name()));
    }


    // --- Error Messages (Can remain constants if they are distinct enough) ---
    public static final String INTERNAL_SERVER_ERROR = "An unexpected internal server error occurred. Please try again later.";
    public static final String RESOURCE_NOT_FOUND = "The requested resource was not found."; // generic version
    public static final String USER_ALREADY_EXISTS = "User already exists."; // specific to user
    public static final String USER_NOT_FOUND = "User not found."; // specific to user
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists."; //specific to user email
    public static final String INVALID_USER_TYPE = "Invalid user type provided.";

    // // --- Helper Method ---
    private static String capitalize(String str) {
        if (str == null || str.isBlank()) {
            return "";
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1).toLowerCase();
    }

    private FeedbackMessage() {
        // Prevent instantiation
    }
}
