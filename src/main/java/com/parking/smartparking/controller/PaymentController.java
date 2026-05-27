package com.parking.smartparking.controller;

import com.parking.smartparking.dto.ApiResponse;
import com.parking.smartparking.dto.PaymentRequest;
import com.parking.smartparking.dto.PaymentResponse;
import com.parking.smartparking.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for payment management.
 * Handles payment processing, retrieval, and revenue reporting.
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Management", description = "APIs for managing payments")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Process payment for a booking.
     * Calculates fee, creates payment record, releases slot.
     */
    @PostMapping
    @Operation(summary = "Process payment", description = "Processes payment for a booking after vehicle exit")
    public ResponseEntity<ApiResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse payment = paymentService.processPayment(request);
        return new ResponseEntity<>(
                ApiResponse.builder().success(true).message("Payment processed successfully").data(payment).build(),
                HttpStatus.CREATED
        );
    }

    /** Get payment details for a specific booking. */
    @GetMapping("/booking/{bookingId}")
    @Operation(summary = "Get payment by booking", description = "Retrieves payment details for a specific booking")
    public ResponseEntity<ApiResponse> getPaymentByBooking(@PathVariable Long bookingId) {
        PaymentResponse payment = paymentService.getPaymentByBooking(bookingId);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Payment retrieved successfully").data(payment).build()
        );
    }

    /** Get all payments in the system. */
    @GetMapping
    @Operation(summary = "Get all payments", description = "Retrieves all payment records")
    public ResponseEntity<ApiResponse> getAllPayments() {
        List<PaymentResponse> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Payments retrieved successfully").data(payments).build()
        );
    }

    /** Get total revenue from all completed payments. */
    @GetMapping("/revenue")
    @Operation(summary = "Get total revenue", description = "Retrieves total revenue from all paid payments")
    public ResponseEntity<ApiResponse> getTotalRevenue() {
        Double revenue = paymentService.getTotalRevenue();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Revenue retrieved successfully").data(revenue).build()
        );
    }
}
