package com.parking.smartparking.service;

import com.parking.smartparking.dto.BookingResponse;
import com.parking.smartparking.dto.PaymentResponse;
import com.parking.smartparking.enums.BookingStatus;
import com.parking.smartparking.enums.SlotStatus;
import com.parking.smartparking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for generating reports and dashboard statistics.
 * Aggregates data from multiple repositories to provide system-wide insights.
 */
@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;
    private final ParkingLocationRepository parkingLocationRepository;
    private final SlotRepository slotRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final TowingRepository towingRepository;
    private final BookingService bookingService;
    private final PaymentService paymentService;

    /**
     * Generates a comprehensive dashboard statistics map.
     * Includes counts for users, locations, slots (by status), bookings (active),
     * payments, revenue, and towed vehicles.
     *
     * @return a map of statistic keys to their values
     */
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new LinkedHashMap<>();

        stats.put("totalUsers", userRepository.count());
        stats.put("totalLocations", parkingLocationRepository.count());
        stats.put("totalSlots", slotRepository.count());
        stats.put("availableSlots", slotRepository.countByStatus(SlotStatus.AVAILABLE));
        stats.put("bookedSlots", slotRepository.countByStatus(SlotStatus.BOOKED));
        stats.put("occupiedSlots", slotRepository.countByStatus(SlotStatus.OCCUPIED));
        stats.put("totalBookings", bookingRepository.count());
        stats.put("activeBookings", bookingRepository.countByStatus(BookingStatus.ENTERED));
        stats.put("totalPayments", paymentRepository.count());

        Double revenue = paymentRepository.getTotalRevenue();
        stats.put("totalRevenue", revenue != null ? revenue : 0.0);

        stats.put("towedVehicles", towingRepository.count());

        return stats;
    }

    /**
     * Retrieves all bookings as a report.
     *
     * @return list of all BookingResponse objects
     */
    public List<BookingResponse> getAllBookingsReport() {
        return bookingService.getAllBookings();
    }

    /**
     * Retrieves all payments as a report.
     *
     * @return list of all PaymentResponse objects
     */
    public List<PaymentResponse> getAllPaymentsReport() {
        return paymentService.getAllPayments();
    }

    /**
     * Retrieves the total revenue as a report figure.
     *
     * @return the total revenue amount
     */
    public Double getRevenueReport() {
        return paymentService.getTotalRevenue();
    }
}
