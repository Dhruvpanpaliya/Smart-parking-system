package com.parking.smartparking.exception;

/**
 * Exception thrown when a requested parking slot is not available for booking.
 */
public class SlotUnavailableException extends RuntimeException {

    public SlotUnavailableException(String message) {
        super(message);
    }
}
