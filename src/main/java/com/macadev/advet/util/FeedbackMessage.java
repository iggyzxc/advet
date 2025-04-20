package com.macadev.advet.util;

/**
 * Utility class containing standardized feedback messages for API responses.
 * This class is final and cannot be instantiated.
 */
public final class FeedbackMessage {
    // --- Success Messages
    public static final String REGISTER_SUCCESS = "User created successfully.";
    public static final String UPDATE_SUCCESS = "User updated successfully.";
    public static final String FOUND_SUCCESS = "User found successfully.";
    public static final String DELETE_SUCCESS = "User deleted successfully.";

    // --- Error Messages
    public static final String INTERNAL_SERVER_ERROR = "An unexpected internal server error occurred. Please try again later.";
    public static final String RESOURCE_NOT_FOUND = "The requested resource was not found.";
    public static final String USER_ALREADY_EXISTS = "User already exists.";
    public static final String USER_NOT_FOUND = "User not found.";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists.";
    public static final String INVALID_USER_TYPE = "Invalid user type provided.";

    private FeedbackMessage() {
        // Prevent instantiation
    }
}
