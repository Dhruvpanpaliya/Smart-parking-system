package com.parking.smartparking.repository;

import com.parking.smartparking.entity.Slot;
import com.parking.smartparking.enums.SlotStatus;
import com.parking.smartparking.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Slot entity CRUD and custom query operations.
 */
@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    List<Slot> findByStatus(SlotStatus status);

    List<Slot> findByParkingLocationIdAndStatus(Long locationId, SlotStatus status);

    List<Slot> findByParkingLocationIdAndVehicleTypeAndStatus(Long locationId, VehicleType type, SlotStatus status);

    List<Slot> findByParkingLocationId(Long locationId);

    Long countByStatus(SlotStatus status);
}
