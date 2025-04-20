package com.macadev.advet.util;

/**
 * Utility class containing constant definitions for API URL paths.
 * Follows RESTful conventions where paths identify resources (nouns)
 * and HTTP methods define actions (verbs).
 * This class is final and cannot be instantiated.
 */
public final class UrlMapping {

    // --- Base API Path ---
    public static final String API_BASE_V1 = "/api/v1";

    // --- User Resource Paths ---
    public static final String USERS_BASE = API_BASE_V1 + "/users";
    public static final String USER_ID_VARIABLE = "/{userId}";

    private UrlMapping() {
        // Private constructor to prevent instantiation
    }
}
