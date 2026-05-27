package com.parking.smartparking.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO for booking response data sent back to the client.
 * Contains all relevant booking details including user, slot, and location info.
 */
@Data
@Builder
public class BookingResponse {

    private Long bookingId;
    private String userName;
    private String slotNumber;
    private String locationName;
    private String vehicleNumber;
    private String vehicleType;
    private String status;
    private LocalDateTime bookingTime;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String qrCode;
}
