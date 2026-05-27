package com.parking.smartparking.service;

import com.parking.smartparking.entity.Booking;
import com.parking.smartparking.exception.InvalidBookingException;
import com.parking.smartparking.exception.ResourceNotFoundException;
import com.parking.smartparking.repository.BookingRepository;
import com.parking.smartparking.util.FeeCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for calculating parking fees based on duration and vehicle type.
 * Delegates fee computation to the FeeCalculator utility.
 */
@Service
@RequiredArgsConstructor
public class BillingService {

    private final BookingRepository bookingRepository;

    /**
     * Calculates the parking fee for a completed booking session.
     * Requires both entry and exit times to be recorded on the booking.
     *
     * @param bookingId the booking ID to calculate the fee for
     * @return the calculated fee amount
     * @throws ResourceNotFoundException if the booking is not found
     * @throws InvalidBookingException   if entry or exit time is missing
     */
    public Double calculateFee(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + bookingId));

        // Ensure both timestamps are present for fee calculation
        if (booking.getEntryTime() == null) {
            throw new InvalidBookingException(
                    "Cannot calculate fee: entry time is not recorded for booking id: " + bookingId);
        }
        if (booking.getExitTime() == null) {
            throw new InvalidBookingException(
                    "Cannot calculate fee: exit time is not recorded for booking id: " + bookingId);
        }

        return FeeCalculator.calculateFee(
                booking.getEntryTime(),
                booking.getExitTime(),
                booking.getSlot().getVehicleType());
    }
}
