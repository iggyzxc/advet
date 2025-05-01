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
        return String.format("%s created successfully.", resourceType.getDisplayName());
    }

    public static String updateSuccess(ResourceType resourceType) {
        if (resourceType == null) {
            return "Resource updated successfully.";
        }
        return String.format("%s updated successfully.", resourceType.getDisplayName());
    }

    public static String foundSuccess(ResourceType resourceType) {
        if (resourceType == null) {
            return "Resource found successfully.";
        }
        return String.format("%s found successfully.", resourceType.getDisplayName());
    }

    public static String deleteSuccess(ResourceType resourceType) {
        if (resourceType == null) {
            return "Resource deleted successfully.";
        }
        return String.format("%s deleted successfully.", resourceType.getDisplayName());
    }


    // --- Error Messages (Can remain constants if they are distinct enough) ---
    public static String noResourceFound(ResourceType resourceType) {
        if (resourceType == null) {
            return "No resource found.";
        }
        return String.format("No %s found.", resourceType.getDisplayName());
        }

    public static final String INTERNAL_SERVER_ERROR = "An unexpected internal server error occurred. Please try again later.";

    private FeedbackMessage() {
        // Prevent instantiation
    }
}
