package com.parking.smartparking.repository;

import com.parking.smartparking.entity.TowingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for TowingRequest entity CRUD and custom query operations.
 */
@Repository
public interface TowingRepository extends JpaRepository<TowingRequest, Long> {

    Optional<TowingRequest> findByBookingId(Long bookingId);
}
