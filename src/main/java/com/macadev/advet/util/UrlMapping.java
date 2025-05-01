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

    // --- Appointment Resource Paths ---
    public static final String APPOINTMENTS_BASE = API_BASE_V1 + "/appointments";
    public static final String APPOINTMENT_ID_VARIABLE = "/{appointmentId}";
    public static final String APPOINTMENT_NUMBER_VARIABLE = "/appointment-number/{appointmentNumber}";


    // --- Pet Resource Paths ---
    public static final String PETS_BASE = API_BASE_V1 + "/pets";
    public static final String PET_ID_VARIABLE = "/{petId}";

    private UrlMapping() {
        // Private constructor to prevent instantiation
    }
}
