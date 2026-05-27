package com.parking.smartparking.repository;

import com.parking.smartparking.entity.Booking;
import com.parking.smartparking.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for Booking entity CRUD and custom query operations.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    List<Booking> findByStatus(BookingStatus status);

    List<Booking> findBySlotId(Long slotId);

    Long countByStatus(BookingStatus status);

    /**
     * Finds all bookings where the vehicle has entered but stayed beyond the given threshold time.
     * Used for identifying vehicles eligible for towing.
     */
    @Query("SELECT b FROM Booking b WHERE b.status = 'ENTERED' AND b.entryTime < :threshold")
    List<Booking> findOverstayedBookings(@Param("threshold") LocalDateTime threshold);
}
