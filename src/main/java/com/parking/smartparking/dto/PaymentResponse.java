package com.parking.smartparking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for payment response data sent back to the client.
 */
@Data
@Builder
public class PaymentResponse {

    private Long paymentId;
    private Long bookingId;
    private Double amount;
    private String status;
    private LocalDateTime paymentTime;
}
