package com.parking.smartparking.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parking.smartparking.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a parking booking made by a user for a specific slot.
 * Tracks the full lifecycle: BOOKED -> ENTERED -> EXITED -> PAID (or TOWED).
 */
@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    private LocalDateTime bookingTime = LocalDateTime.now();

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private BookingStatus status = BookingStatus.BOOKED;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String qrCode;

    private String vehicleNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"bookings", "password"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    @JsonIgnoreProperties({"bookings"})
    private Slot slot;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private TowingRequest towingRequest;

    @PrePersist
    protected void onCreate() {
        if (bookingTime == null) {
            bookingTime = LocalDateTime.now();
        }
    }
}
