package com.parking.smartparking.controller;

import com.parking.smartparking.dto.ApiResponse;
import com.parking.smartparking.dto.BookingResponse;
import com.parking.smartparking.dto.PaymentResponse;
import com.parking.smartparking.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for reports and dashboard.
 * Admin can view system statistics, booking reports, payment reports, and revenue.
 */
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports & Dashboard", description = "APIs for viewing system reports and analytics")
public class ReportController {

    private final ReportService reportService;

    /**
     * Get dashboard statistics.
     * Returns aggregated data: total users, locations, slots, bookings, revenue, etc.
     */
    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard stats", description = "Retrieves aggregated dashboard statistics")
    public ResponseEntity<ApiResponse> getDashboardStats() {
        Map<String, Object> stats = reportService.getDashboardStats();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Dashboard stats retrieved successfully").data(stats).build()
        );
    }

    /** Get all bookings as a report. */
    @GetMapping("/bookings")
    @Operation(summary = "Get bookings report", description = "Retrieves all bookings for reporting")
    public ResponseEntity<ApiResponse> getBookingsReport() {
        List<BookingResponse> bookings = reportService.getAllBookingsReport();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Bookings report retrieved successfully").data(bookings).build()
        );
    }

    /** Get all payments as a report. */
    @GetMapping("/payments")
    @Operation(summary = "Get payments report", description = "Retrieves all payments for reporting")
    public ResponseEntity<ApiResponse> getPaymentsReport() {
        List<PaymentResponse> payments = reportService.getAllPaymentsReport();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Payments report retrieved successfully").data(payments).build()
        );
    }

    /** Get total revenue report. */
    @GetMapping("/revenue")
    @Operation(summary = "Get revenue report", description = "Retrieves total revenue from the system")
    public ResponseEntity<ApiResponse> getRevenueReport() {
        Double revenue = reportService.getRevenueReport();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Revenue report retrieved successfully").data(revenue).build()
        );
    }
}
