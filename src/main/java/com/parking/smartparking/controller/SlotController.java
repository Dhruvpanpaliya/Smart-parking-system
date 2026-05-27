package com.parking.smartparking.controller;

import com.parking.smartparking.dto.ApiResponse;
import com.parking.smartparking.entity.ParkingLocation;
import com.parking.smartparking.entity.Slot;
import com.parking.smartparking.enums.VehicleType;
import com.parking.smartparking.service.ParkingLocationService;
import com.parking.smartparking.service.SlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for parking slot management.
 * Provides endpoints for CRUD operations on slots and filtering available slots.
 */
@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
@Tag(name = "Parking Slot Management", description = "APIs for managing parking slots")
public class SlotController {

    private final SlotService slotService;
    private final ParkingLocationService parkingLocationService;

    /** Get all parking slots. */
    @GetMapping
    @Operation(summary = "Get all slots", description = "Retrieves all parking slots")
    public ResponseEntity<ApiResponse> getAllSlots() {
        List<Slot> slots = slotService.getAllSlots();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Slots retrieved successfully").data(slots).build()
        );
    }

    /** Get a specific slot by ID. */
    @GetMapping("/{id}")
    @Operation(summary = "Get slot by ID", description = "Retrieves a parking slot by its ID")
    public ResponseEntity<ApiResponse> getSlotById(@PathVariable Long id) {
        Slot slot = slotService.getSlotById(id);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Slot retrieved successfully").data(slot).build()
        );
    }

    /** Get slots by parking location. */
    @GetMapping("/location/{locationId}")
    @Operation(summary = "Get slots by location", description = "Retrieves all slots for a specific parking location")
    public ResponseEntity<ApiResponse> getSlotsByLocation(@PathVariable Long locationId) {
        List<Slot> slots = slotService.getSlotsByLocation(locationId);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Slots retrieved successfully").data(slots).build()
        );
    }

    /** Get all available slots across all locations. */
    @GetMapping("/available")
    @Operation(summary = "Get available slots", description = "Retrieves all available parking slots")
    public ResponseEntity<ApiResponse> getAvailableSlots() {
        List<Slot> slots = slotService.getAvailableSlots();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Available slots retrieved successfully").data(slots).build()
        );
    }

    /** Get available slots for a specific location. */
    @GetMapping("/available/{locationId}")
    @Operation(summary = "Get available slots by location", description = "Retrieves available slots for a specific location")
    public ResponseEntity<ApiResponse> getAvailableSlotsByLocation(@PathVariable Long locationId) {
        List<Slot> slots = slotService.getAvailableSlotsByLocation(locationId);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Available slots retrieved successfully").data(slots).build()
        );
    }

    /** Get available slots filtered by location and vehicle type. */
    @GetMapping("/available/{locationId}/{vehicleType}")
    @Operation(summary = "Get available slots by location and vehicle type", description = "Filter available slots by location and vehicle type")
    public ResponseEntity<ApiResponse> getAvailableSlotsByLocationAndType(
            @PathVariable Long locationId, @PathVariable VehicleType vehicleType) {
        List<Slot> slots = slotService.getAvailableSlotsByLocationAndType(locationId, vehicleType);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Available slots retrieved successfully").data(slots).build()
        );
    }

    /** Create a new parking slot. The request body must include a locationId field. */
    @PostMapping
    @Operation(summary = "Create slot", description = "Creates a new parking slot linked to a location")
    public ResponseEntity<ApiResponse> createSlot(@Valid @RequestBody Slot slot, @RequestParam Long locationId) {
        ParkingLocation location = parkingLocationService.getLocationById(locationId);
        slot.setParkingLocation(location);
        Slot created = slotService.createSlot(slot);
        return new ResponseEntity<>(
                ApiResponse.builder().success(true).message("Slot created successfully").data(created).build(),
                HttpStatus.CREATED
        );
    }

    /** Update an existing parking slot. */
    @PutMapping("/{id}")
    @Operation(summary = "Update slot", description = "Updates an existing parking slot")
    public ResponseEntity<ApiResponse> updateSlot(@PathVariable Long id, @Valid @RequestBody Slot slot) {
        Slot updated = slotService.updateSlot(id, slot);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Slot updated successfully").data(updated).build()
        );
    }

    /** Delete a parking slot. */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete slot", description = "Deletes a parking slot")
    public ResponseEntity<ApiResponse> deleteSlot(@PathVariable Long id) {
        slotService.deleteSlot(id);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Slot deleted successfully").data(null).build()
        );
    }
}
