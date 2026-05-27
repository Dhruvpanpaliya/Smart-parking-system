package com.parking.smartparking.controller;

import com.parking.smartparking.dto.ApiResponse;
import com.parking.smartparking.dto.BookingResponse;
import com.parking.smartparking.entity.TowingRequest;
import com.parking.smartparking.service.TowingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for towing management.
 * Admin can detect overstayed vehicles and manage towing operations.
 */
@RestController
@RequestMapping("/api/towing")
@RequiredArgsConstructor
@Tag(name = "Towing Management", description = "APIs for managing vehicle towing operations")
public class TowingController {

    private final TowingService towingService;

    /**
     * Detect vehicles that have been parked for more than 24 hours.
     * Updates their booking status to TOWED.
     */
    @PostMapping("/detect")
    @Operation(summary = "Detect overstayed vehicles", description = "Finds and marks vehicles parked beyond the allowed 24-hour duration")
    public ResponseEntity<ApiResponse> detectOverstayedVehicles() {
        List<BookingResponse> towedBookings = towingService.detectOverstayedVehicles();
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Overstayed vehicles detected: " + towedBookings.size())
                        .data(towedBookings)
                        .build()
        );
    }

    /**
     * Create a towing request for a specific booking.
     * Records towing details such as location, contact info, and reason.
     */
    @PostMapping("/{bookingId}")
    @Operation(summary = "Create towing request", description = "Creates a towing request for a specific booking")
    public ResponseEntity<ApiResponse> createTowingRequest(
            @PathVariable Long bookingId, @RequestBody TowingRequest request) {
        TowingRequest towingRequest = towingService.createTowingRequest(bookingId, request);
        return new ResponseEntity<>(
                ApiResponse.builder().success(true).message("Towing request created successfully").data(towingRequest).build(),
                HttpStatus.CREATED
        );
    }

    /** Get all towing requests in the system. */
    @GetMapping
    @Operation(summary = "Get all towing requests", description = "Retrieves all towing requests")
    public ResponseEntity<ApiResponse> getAllTowingRequests() {
        List<TowingRequest> requests = towingService.getAllTowingRequests();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Towing requests retrieved successfully").data(requests).build()
        );
    }

    /** Get towing request for a specific booking. */
    @GetMapping("/booking/{bookingId}")
    @Operation(summary = "Get towing by booking", description = "Retrieves towing request for a specific booking")
    public ResponseEntity<ApiResponse> getTowingByBooking(@PathVariable Long bookingId) {
        TowingRequest request = towingService.getTowingByBooking(bookingId);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Towing request retrieved successfully").data(request).build()
        );
    }
}
