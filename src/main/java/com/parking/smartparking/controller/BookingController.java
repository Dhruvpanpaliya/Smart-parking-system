package com.parking.smartparking.controller;

import com.parking.smartparking.dto.ApiResponse;
import com.parking.smartparking.dto.BookingRequest;
import com.parking.smartparking.dto.BookingResponse;
import com.parking.smartparking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for booking management.
 * Users can book slots, view bookings, and cancel bookings.
 */
@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Management", description = "APIs for managing parking slot bookings")
public class BookingController {

    private final BookingService bookingService;

    /**
     * Create a new parking booking.
     * Assigns slot to user and generates QR code.
     */
    @PostMapping
    @Operation(summary = "Create booking", description = "Books an available parking slot for a user")
    public ResponseEntity<ApiResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        BookingResponse booking = bookingService.createBooking(request);
        return new ResponseEntity<>(
                ApiResponse.builder().success(true).message("Booking created successfully").data(booking).build(),
                HttpStatus.CREATED
        );
    }

    /** Get all bookings in the system (admin use). */
    @GetMapping
    @Operation(summary = "Get all bookings", description = "Retrieves all bookings in the system")
    public ResponseEntity<ApiResponse> getAllBookings() {
        List<BookingResponse> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Bookings retrieved successfully").data(bookings).build()
        );
    }

    /** Get a specific booking by ID. */
    @GetMapping("/{id}")
    @Operation(summary = "Get booking by ID", description = "Retrieves a specific booking by its ID")
    public ResponseEntity<ApiResponse> getBookingById(@PathVariable Long id) {
        BookingResponse booking = bookingService.getBookingById(id);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Booking retrieved successfully").data(booking).build()
        );
    }

    /** Get all bookings for a specific user. */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get bookings by user", description = "Retrieves all bookings for a specific user")
    public ResponseEntity<ApiResponse> getBookingsByUser(@PathVariable Long userId) {
        List<BookingResponse> bookings = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("User bookings retrieved successfully").data(bookings).build()
        );
    }

    /** Cancel an existing booking (only if status is BOOKED). */
    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel booking", description = "Cancels a booking and releases the slot")
    public ResponseEntity<ApiResponse> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Booking cancelled successfully").data(null).build()
        );
    }
}
