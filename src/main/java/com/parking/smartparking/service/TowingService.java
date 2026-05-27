package com.parking.smartparking.service;

import com.parking.smartparking.dto.BookingResponse;
import com.parking.smartparking.entity.Booking;
import com.parking.smartparking.entity.TowingRequest;
import com.parking.smartparking.enums.BookingStatus;
import com.parking.smartparking.exception.ResourceNotFoundException;
import com.parking.smartparking.repository.BookingRepository;
import com.parking.smartparking.repository.TowingRepository;
import com.parking.smartparking.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing vehicle towing operations.
 * Detects overstayed vehicles and handles towing request creation and retrieval.
 */
@Service
@RequiredArgsConstructor
public class TowingService {

    private final BookingRepository bookingRepository;
    private final TowingRepository towingRepository;
    private final BookingService bookingService;

    /**
     * Detects vehicles that have overstayed beyond the allowed threshold.
     * Marks overstayed bookings as TOWED.
     *
     * @return list of BookingResponse objects for the towed bookings
     */
    @Transactional
    public List<BookingResponse> detectOverstayedVehicles() {
        // Calculate the overstay threshold (current time minus allowed hours)
        LocalDateTime threshold = LocalDateTime.now()
                .minusHours(AppConstants.TOWING_THRESHOLD_HOURS);

        // Find all bookings that have exceeded the stay limit
        List<Booking> overstayedBookings = bookingRepository.findOverstayedBookings(threshold);

        // Mark each overstayed booking as TOWED
        overstayedBookings.forEach(booking -> {
            booking.setStatus(BookingStatus.TOWED);
            bookingRepository.save(booking);
        });

        return overstayedBookings.stream()
                .map(bookingService::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    /**
     * Creates a towing request for a specific booking.
     *
     * @param bookingId the booking ID to associate the towing request with
     * @param request   the towing request details
     * @return the saved TowingRequest entity
     * @throws ResourceNotFoundException if the booking is not found
     */
    @Transactional
    public TowingRequest createTowingRequest(Long bookingId, TowingRequest request) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + bookingId));

        request.setBooking(booking);
        return towingRepository.save(request);
    }

    /**
     * Retrieves all towing requests in the system.
     *
     * @return list of all TowingRequest entities
     */
    public List<TowingRequest> getAllTowingRequests() {
        return towingRepository.findAll();
    }

    /**
     * Retrieves the towing request associated with a specific booking.
     *
     * @param bookingId the booking ID
     * @return the TowingRequest entity
     * @throws ResourceNotFoundException if no towing request is found
     */
    public TowingRequest getTowingByBooking(Long bookingId) {
        return towingRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Towing request not found for booking id: " + bookingId));
    }
}
