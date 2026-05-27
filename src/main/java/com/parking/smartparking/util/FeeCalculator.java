package com.parking.smartparking.util;

import com.parking.smartparking.enums.VehicleType;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Utility class for calculating parking fees based on
 * vehicle type and parking duration.
 */
public final class FeeCalculator {

    private FeeCalculator() {
        // Prevent instantiation
    }

    /**
     * Calculates the parking fee based on entry time, exit time, and vehicle type.
     * Duration is rounded up to a minimum of 1 hour.
     *
     * @param entry      the entry timestamp
     * @param exit       the exit timestamp
     * @param vehicleType the type of vehicle (CAR, BIKE, TRUCK)
     * @return the calculated parking fee
     */
    public static double calculateFee(LocalDateTime entry, LocalDateTime exit, VehicleType vehicleType) {
        long minutes = Duration.between(entry, exit).toMinutes();

        // Round up to the nearest hour, with a minimum of 1 hour
        long hours = (minutes + 59) / 60;
        if (hours < 1) {
            hours = 1;
        }

        double ratePerHour = switch (vehicleType) {
            case CAR -> AppConstants.CAR_RATE_PER_HOUR;
            case BIKE -> AppConstants.BIKE_RATE_PER_HOUR;
            case TRUCK -> AppConstants.TRUCK_RATE_PER_HOUR;
        };

        return hours * ratePerHour;
    }
}
