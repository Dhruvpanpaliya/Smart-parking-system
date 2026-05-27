package com.parking.smartparking.service;

import com.parking.smartparking.dto.BookingRequest;
import com.parking.smartparking.dto.BookingResponse;
import com.parking.smartparking.entity.Booking;
import com.parking.smartparking.entity.Slot;
import com.parking.smartparking.entity.User;
import com.parking.smartparking.enums.BookingStatus;
import com.parking.smartparking.enums.SlotStatus;
import com.parking.smartparking.exception.InvalidBookingException;
import com.parking.smartparking.exception.ResourceNotFoundException;
import com.parking.smartparking.exception.SlotUnavailableException;
import com.parking.smartparking.repository.BookingRepository;
import com.parking.smartparking.repository.SlotRepository;
import com.parking.smartparking.repository.UserRepository;
import com.parking.smartparking.util.QRUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing parking bookings.
 * Handles booking creation, retrieval, cancellation, and status transitions.
 * Coordinates slot availability and QR code generation.
 */
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SlotRepository slotRepository;

    /**
     * Creates a new parking booking.
     * Validates user existence, slot existence, and slot availability.
     * Generates a QR code for the booking and marks the slot as BOOKED.
     *
     * @param request the booking request containing userId, slotId, and vehicleNumber
     * @return the created BookingResponse
     * @throws ResourceNotFoundException if user or slot is not found
     * @throws SlotUnavailableException  if the slot is not available
     */
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        // Validate user exists
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + request.getUserId()));

        // Validate slot exists
        Slot slot = slotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Slot not found with id: " + request.getSlotId()));

        // Ensure slot is available for booking
        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new SlotUnavailableException(
                    "Slot is not available. Current status: " + slot.getStatus());
        }

        // Build the booking entity
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSlot(slot);
        booking.setVehicleNumber(request.getVehicleNumber());
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.BOOKED);

        // Persist to obtain the generated ID
        booking = bookingRepository.save(booking);

        // Generate QR code with booking info
        String qrText = "BOOKING-" + booking.getId() + "-" + booking.getVehicleNumber();
        String qrCode = QRUtil.generateQRCodeBase64(qrText, 250, 250);
        booking.setQrCode(qrCode);
        booking = bookingRepository.save(booking);

        // Mark slot as booked
        slot.setStatus(SlotStatus.BOOKED);
        slotRepository.save(slot);

        return convertToBookingResponse(booking);
    }

    /**
     * Retrieves a booking by its unique identifier.
     *
     * @param id the booking ID
     * @return the BookingResponse
     * @throws ResourceNotFoundException if no booking is found
     */
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
        return convertToBookingResponse(booking);
    }

    /**
     * Retrieves all bookings for a specific user.
     *
     * @param userId the user ID
     * @return list of BookingResponse objects
     */
    public List<BookingResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all bookings in the system.
     *
     * @return list of all BookingResponse objects
     */
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    /**
     * Cancels a booking if it is still in BOOKED status.
     * Releases the associated slot back to AVAILABLE.
     *
     * @param id the booking ID to cancel
     * @throws ResourceNotFoundException if no booking is found
     * @throws InvalidBookingException   if the booking is not in BOOKED status
     */
    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));

        // Only BOOKED bookings can be cancelled
        if (booking.getStatus() != BookingStatus.BOOKED) {
            throw new InvalidBookingException(
                    "Cannot cancel booking. Current status: " + booking.getStatus());
        }

        // Release the slot
        Slot slot = booking.getSlot();
        slot.setStatus(SlotStatus.AVAILABLE);
        slotRepository.save(slot);

        // Remove the booking
        bookingRepository.delete(booking);
    }

    /**
     * Counts bookings by status.
     *
     * @param status the booking status
     * @return the count of bookings with the given status
     */
    public Long countByStatus(BookingStatus status) {
        return bookingRepository.countByStatus(status);
    }

    /**
     * Converts a Booking entity to a BookingResponse DTO.
     *
     * @param booking the Booking entity
     * @return the corresponding BookingResponse
     */
    public BookingResponse convertToBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .bookingId(booking.getId())
                .userName(booking.getUser().getName())
                .slotNumber(booking.getSlot().getSlotNumber())
                .locationName(booking.getSlot().getParkingLocation().getName())
                .vehicleNumber(booking.getVehicleNumber())
                .vehicleType(booking.getSlot().getVehicleType().name())
                .status(booking.getStatus().name())
                .bookingTime(booking.getBookingTime())
                .entryTime(booking.getEntryTime())
                .exitTime(booking.getExitTime())
                .qrCode(booking.getQrCode())
                .build();
    }
}
