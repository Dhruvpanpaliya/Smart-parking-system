package com.parking.smartparking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for payment processing requests.
 */
@Data
public class PaymentRequest {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;
}
