package com.parking.smartparking.service;

import com.parking.smartparking.dto.PaymentRequest;
import com.parking.smartparking.dto.PaymentResponse;
import com.parking.smartparking.entity.Booking;
import com.parking.smartparking.entity.Payment;
import com.parking.smartparking.entity.Slot;
import com.parking.smartparking.enums.BookingStatus;
import com.parking.smartparking.enums.PaymentStatus;
import com.parking.smartparking.enums.SlotStatus;
import com.parking.smartparking.exception.InvalidBookingException;
import com.parking.smartparking.exception.ResourceNotFoundException;
import com.parking.smartparking.repository.BookingRepository;
import com.parking.smartparking.repository.PaymentRepository;
import com.parking.smartparking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for processing and managing parking payments.
 * Handles payment creation, slot release upon payment, and revenue queries.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final SlotRepository slotRepository;
    private final BillingService billingService;

    /**
     * Processes payment for a completed parking session.
     * Calculates the fee, creates a payment record, updates booking status to PAID,
     * and releases the slot back to AVAILABLE.
     *
     * @param request the payment request containing bookingId
     * @return the PaymentResponse with payment details
     * @throws ResourceNotFoundException if the booking is not found
     * @throws InvalidBookingException   if the booking is not in EXITED status
     */
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + request.getBookingId()));

        // Payment can only be processed after vehicle has exited
        if (booking.getStatus() != BookingStatus.EXITED) {
            throw new InvalidBookingException(
                    "Cannot process payment. Booking status must be EXITED. Current status: "
                            + booking.getStatus());
        }

        // Calculate fee based on parking duration
        Double feeAmount = billingService.calculateFee(booking.getId());

        // Create and save payment record
        Payment payment = new Payment();
        payment.setAmount(feeAmount);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PAID);
        payment.setBooking(booking);
        payment = paymentRepository.save(payment);

        // Mark booking as paid
        booking.setStatus(BookingStatus.PAID);
        booking.setPayment(payment);
        bookingRepository.save(booking);

        // Release the parking slot
        Slot slot = booking.getSlot();
        slot.setStatus(SlotStatus.AVAILABLE);
        slotRepository.save(slot);

        return convertToPaymentResponse(payment);
    }

    /**
     * Retrieves the payment associated with a specific booking.
     *
     * @param bookingId the booking ID
     * @return the PaymentResponse
     * @throws ResourceNotFoundException if no payment is found for the booking
     */
    public PaymentResponse getPaymentByBooking(Long bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found for booking id: " + bookingId));
        return convertToPaymentResponse(payment);
    }

    /**
     * Retrieves all payments in the system.
     *
     * @return list of all PaymentResponse objects
     */
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::convertToPaymentResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the total revenue generated from all payments.
     *
     * @return the total revenue amount, or 0.0 if no payments exist
     */
    public Double getTotalRevenue() {
        Double revenue = paymentRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }

    /**
     * Converts a Payment entity to a PaymentResponse DTO.
     *
     * @param payment the Payment entity
     * @return the corresponding PaymentResponse
     */
    private PaymentResponse convertToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .bookingId(payment.getBooking().getId())
                .amount(payment.getAmount())
                .status(payment.getStatus().name())
                .paymentTime(payment.getPaymentTime())
                .build();
    }
}
