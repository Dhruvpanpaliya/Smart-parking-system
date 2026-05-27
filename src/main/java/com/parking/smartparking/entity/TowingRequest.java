package com.parking.smartparking.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing a towing request for vehicles that overstay
 * beyond the allowed parking duration threshold.
 */
@Entity
@Table(name = "towing_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TowingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime towingDate;

    private String towingLocation;

    private String contactInfo;

    private String reason;

    @OneToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnoreProperties({"towingRequest", "payment"})
    private Booking booking;
}
