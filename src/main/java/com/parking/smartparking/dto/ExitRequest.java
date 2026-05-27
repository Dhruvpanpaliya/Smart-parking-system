package com.parking.smartparking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for vehicle exit requests from the parking location.
 */
@Data
public class ExitRequest {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;
}
