package com.parking.smartparking.util;

/**
 * Application-wide constants for the Smart Parking Management System.
 * Contains rate configurations, thresholds, and default credentials.
 */
public final class AppConstants {

    private AppConstants() {
        // Prevent instantiation
    }

    /** Number of hours after which a vehicle is eligible for towing */
    public static final int TOWING_THRESHOLD_HOURS = 24;

    /** Hourly parking rate for cars (in currency units) */
    public static final double CAR_RATE_PER_HOUR = 50.0;

    /** Hourly parking rate for bikes (in currency units) */
    public static final double BIKE_RATE_PER_HOUR = 20.0;

    /** Hourly parking rate for trucks (in currency units) */
    public static final double TRUCK_RATE_PER_HOUR = 100.0;

    /** Default admin email for seeded data */
    public static final String DEFAULT_ADMIN_EMAIL = "admin@smartparking.com";

    /** Default admin password for seeded data */
    public static final String DEFAULT_ADMIN_PASSWORD = "admin123";
}
