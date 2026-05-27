package com.parking.smartparking.service;

import com.parking.smartparking.dto.BookingResponse;
import com.parking.smartparking.dto.EntryRequest;
import com.parking.smartparking.dto.ExitRequest;
import com.parking.smartparking.entity.Booking;
import com.parking.smartparking.entity.Slot;
import com.parking.smartparking.enums.BookingStatus;
import com.parking.smartparking.enums.SlotStatus;
import com.parking.smartparking.exception.InvalidBookingException;
import com.parking.smartparking.exception.ResourceNotFoundException;
import com.parking.smartparking.repository.BookingRepository;
import com.parking.smartparking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for processing vehicle entry and exit at parking locations.
 * Manages booking status transitions and slot occupancy updates.
 */
@Service
@RequiredArgsConstructor
public class EntryExitService {

    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;
    private final BookingService bookingService;

    /**
     * Processes vehicle entry into the parking facility.
     * Transitions booking from BOOKED to ENTERED and slot from BOOKED to OCCUPIED.
     *
     * @param request the entry request containing bookingId
     * @return the updated BookingResponse
     * @throws ResourceNotFoundException if the booking is not found
     * @throws InvalidBookingException   if the booking is not in BOOKED status
     */
    @Transactional
    public BookingResponse processEntry(EntryRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + request.getBookingId()));

        // Only BOOKED bookings can be used for entry
        if (booking.getStatus() != BookingStatus.BOOKED) {
            throw new InvalidBookingException(
                    "Cannot process entry. Booking status must be BOOKED. Current status: "
                            + booking.getStatus());
        }

        // Record entry time and update status
        booking.setEntryTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.ENTERED);
        bookingRepository.save(booking);

        // Mark slot as physically occupied
        Slot slot = booking.getSlot();
        slot.setStatus(SlotStatus.OCCUPIED);
        slotRepository.save(slot);

        return bookingService.convertToBookingResponse(booking);
    }

    /**
     * Processes vehicle exit from the parking facility.
     * Transitions booking from ENTERED to EXITED.
     * Note: Slot is not released until payment is processed.
     *
     * @param request the exit request containing bookingId
     * @return the updated BookingResponse
     * @throws ResourceNotFoundException if the booking is not found
     * @throws InvalidBookingException   if the booking is not in ENTERED status
     */
    @Transactional
    public BookingResponse processExit(ExitRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + request.getBookingId()));

        // Only ENTERED bookings can exit
        if (booking.getStatus() != BookingStatus.ENTERED) {
            throw new InvalidBookingException(
                    "Cannot process exit. Booking status must be ENTERED. Current status: "
                            + booking.getStatus());
        }

        // Record exit time and update status
        booking.setExitTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.EXITED);
        bookingRepository.save(booking);

        return bookingService.convertToBookingResponse(booking);
    }
}
