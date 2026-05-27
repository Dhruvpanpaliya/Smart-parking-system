package com.parking.smartparking.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parking.smartparking.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a payment transaction associated with a parking booking.
 * Fee is calculated based on vehicle type and parking duration.
 */
@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDateTime paymentTime;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentStatus status = PaymentStatus.PENDING;

    @OneToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnoreProperties({"payment", "towingRequest"})
    private Booking booking;
}
