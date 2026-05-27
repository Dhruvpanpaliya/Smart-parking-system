package com.parking.smartparking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for vehicle entry requests at the parking location.
 */
@Data
public class EntryRequest {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;
}
