package com.parking.smartparking.controller;

import com.parking.smartparking.dto.ApiResponse;
import com.parking.smartparking.dto.BookingResponse;
import com.parking.smartparking.dto.EntryRequest;
import com.parking.smartparking.dto.ExitRequest;
import com.parking.smartparking.service.EntryExitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for vehicle entry and exit management.
 * Security staff uses these endpoints to scan QR codes
 * and manage vehicle entry/exit.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Entry & Exit Management", description = "APIs for managing vehicle entry and exit")
public class EntryExitController {

    private final EntryExitService entryExitService;

    /**
     * Process vehicle entry.
     * Security scans QR code, system validates booking and records entry time.
     * Slot status changes to OCCUPIED, booking status changes to ENTERED.
     */
    @PostMapping("/entry")
    @Operation(summary = "Process vehicle entry", description = "Records vehicle entry and updates booking/slot status")
    public ResponseEntity<ApiResponse> processEntry(@Valid @RequestBody EntryRequest request) {
        BookingResponse booking = entryExitService.processEntry(request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Vehicle entry recorded successfully")
                        .data(booking)
                        .build()
        );
    }

    /**
     * Process vehicle exit.
     * Security scans QR code during exit, system records exit time.
     * Booking status changes to EXITED.
     */
    @PostMapping("/exit")
    @Operation(summary = "Process vehicle exit", description = "Records vehicle exit and calculates duration")
    public ResponseEntity<ApiResponse> processExit(@Valid @RequestBody ExitRequest request) {
        BookingResponse booking = entryExitService.processExit(request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Vehicle exit recorded successfully")
                        .data(booking)
                        .build()
        );
    }
}
