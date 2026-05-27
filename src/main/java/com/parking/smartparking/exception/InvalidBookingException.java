package com.parking.smartparking.exception;

/**
 * Exception thrown when a booking operation is invalid
 * (e.g., trying to enter with an already entered booking).
 */
public class InvalidBookingException extends RuntimeException {

    public InvalidBookingException(String message) {
        super(message);
    }
}
