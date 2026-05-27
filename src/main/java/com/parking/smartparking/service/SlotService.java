package com.parking.smartparking.service;

import com.parking.smartparking.entity.Slot;
import com.parking.smartparking.enums.SlotStatus;
import com.parking.smartparking.enums.VehicleType;
import com.parking.smartparking.exception.ResourceNotFoundException;
import com.parking.smartparking.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for managing parking slot operations.
 * Provides CRUD operations along with availability queries by location and vehicle type.
 */
@Service
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository slotRepository;

    /**
     * Retrieves all parking slots.
     *
     * @return list of all slots
     */
    public List<Slot> getAllSlots() {
        return slotRepository.findAll();
    }

    /**
     * Retrieves a slot by its unique identifier.
     *
     * @param id the slot ID
     * @return the Slot entity
     * @throws ResourceNotFoundException if no slot is found
     */
    public Slot getSlotById(Long id) {
        return slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
    }

    /**
     * Retrieves all slots belonging to a specific parking location.
     *
     * @param locationId the parking location ID
     * @return list of slots at the given location
     */
    public List<Slot> getSlotsByLocation(Long locationId) {
        return slotRepository.findByParkingLocationId(locationId);
    }

    /**
     * Retrieves all currently available slots across all locations.
     *
     * @return list of available slots
     */
    public List<Slot> getAvailableSlots() {
        return slotRepository.findByStatus(SlotStatus.AVAILABLE);
    }

    /**
     * Retrieves available slots at a specific parking location.
     *
     * @param locationId the parking location ID
     * @return list of available slots at the location
     */
    public List<Slot> getAvailableSlotsByLocation(Long locationId) {
        return slotRepository.findByParkingLocationIdAndStatus(locationId, SlotStatus.AVAILABLE);
    }

    /**
     * Retrieves available slots at a location filtered by vehicle type.
     *
     * @param locationId  the parking location ID
     * @param vehicleType the type of vehicle
     * @return list of matching available slots
     */
    public List<Slot> getAvailableSlotsByLocationAndType(Long locationId, VehicleType vehicleType) {
        return slotRepository.findByParkingLocationIdAndVehicleTypeAndStatus(
                locationId, vehicleType, SlotStatus.AVAILABLE);
    }

    /**
     * Creates a new parking slot.
     *
     * @param slot the slot to create
     * @return the saved Slot entity
     */
    public Slot createSlot(Slot slot) {
        slot.setCreatedAt(LocalDateTime.now());
        return slotRepository.save(slot);
    }

    /**
     * Updates an existing parking slot.
     *
     * @param id   the ID of the slot to update
     * @param slot the slot object with updated fields
     * @return the updated Slot entity
     * @throws ResourceNotFoundException if no slot is found
     */
    public Slot updateSlot(Long id, Slot slot) {
        Slot existingSlot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));

        existingSlot.setSlotNumber(slot.getSlotNumber());
        existingSlot.setVehicleType(slot.getVehicleType());
        existingSlot.setStatus(slot.getStatus());

        return slotRepository.save(existingSlot);
    }

    /**
     * Deletes a slot by its unique identifier.
     *
     * @param id the ID of the slot to delete
     * @throws ResourceNotFoundException if no slot is found
     */
    public void deleteSlot(Long id) {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
        slotRepository.delete(slot);
    }

    /**
     * Updates the status of a specific slot.
     *
     * @param id     the slot ID
     * @param status the new slot status
     * @return the updated Slot entity
     * @throws ResourceNotFoundException if no slot is found
     */
    public Slot updateSlotStatus(Long id, SlotStatus status) {
        Slot slot = slotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Slot not found with id: " + id));
        slot.setStatus(status);
        return slotRepository.save(slot);
    }

    /**
     * Counts the number of slots with a given status.
     *
     * @param status the slot status to count
     * @return the count of slots with the specified status
     */
    public Long countByStatus(SlotStatus status) {
        return slotRepository.countByStatus(status);
    }
}
