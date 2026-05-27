package com.parking.smartparking.repository;

import com.parking.smartparking.entity.ParkingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for ParkingLocation entity CRUD and custom query operations.
 */
@Repository
public interface ParkingLocationRepository extends JpaRepository<ParkingLocation, Long> {

    List<ParkingLocation> findByActiveTrue();
}
