package com.parking.smartparking.exception;

/**
 * Exception thrown when a requested resource (User, Slot, Booking, etc.) is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
