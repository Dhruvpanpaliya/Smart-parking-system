package com.parking.smartparking.service;

import com.parking.smartparking.entity.ParkingLocation;
import com.parking.smartparking.exception.ResourceNotFoundException;
import com.parking.smartparking.repository.ParkingLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing parking location CRUD operations.
 * Handles creation, retrieval, update, and deletion of parking locations.
 */
@Service
@RequiredArgsConstructor
public class ParkingLocationService {

    private final ParkingLocationRepository parkingLocationRepository;

    /**
     * Retrieves all parking locations.
     *
     * @return list of all parking locations
     */
    public List<ParkingLocation> getAllLocations() {
        return parkingLocationRepository.findAll();
    }

    /**
     * Retrieves only active parking locations.
     *
     * @return list of active parking locations
     */
    public List<ParkingLocation> getActiveLocations() {
        return parkingLocationRepository.findByActiveTrue();
    }

    /**
     * Retrieves a parking location by its unique identifier.
     *
     * @param id the parking location ID
     * @return the ParkingLocation entity
     * @throws ResourceNotFoundException if no location is found
     */
    public ParkingLocation getLocationById(Long id) {
        return parkingLocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking location not found with id: " + id));
    }

    /**
     * Creates a new parking location.
     *
     * @param location the parking location to create
     * @return the saved ParkingLocation entity
     */
    public ParkingLocation createLocation(ParkingLocation location) {
        location.setCreatedAt(LocalDateTime.now());
        return parkingLocationRepository.save(location);
    }

    /**
     * Updates an existing parking location.
     *
     * @param id       the ID of the location to update
     * @param location the location object with updated fields
     * @return the updated ParkingLocation entity
     * @throws ResourceNotFoundException if no location is found
     */
    public ParkingLocation updateLocation(Long id, ParkingLocation location) {
        ParkingLocation existingLocation = parkingLocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking location not found with id: " + id));

        existingLocation.setName(location.getName());
        existingLocation.setAddress(location.getAddress());
        existingLocation.setTotalSlots(location.getTotalSlots());
        existingLocation.setActive(location.getActive());

        return parkingLocationRepository.save(existingLocation);
    }

    /**
     * Deletes a parking location by its unique identifier.
     *
     * @param id the ID of the location to delete
     * @throws ResourceNotFoundException if no location is found
     */
    public void deleteLocation(Long id) {
        ParkingLocation location = parkingLocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking location not found with id: " + id));
        parkingLocationRepository.delete(location);
    }
}
