package com.parking.smartparking.controller;

import com.parking.smartparking.dto.ApiResponse;
import com.parking.smartparking.entity.ParkingLocation;
import com.parking.smartparking.service.ParkingLocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for parking location management.
 * Admin can create, update, delete, and view parking locations.
 */
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Tag(name = "Parking Location Management", description = "APIs for managing parking locations")
public class ParkingLocationController {

    private final ParkingLocationService parkingLocationService;

    /** Retrieve all parking locations. */
    @GetMapping
    @Operation(summary = "Get all locations", description = "Retrieves all parking locations")
    public ResponseEntity<ApiResponse> getAllLocations() {
        List<ParkingLocation> locations = parkingLocationService.getAllLocations();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Locations retrieved successfully").data(locations).build()
        );
    }

    /** Retrieve only active parking locations. */
    @GetMapping("/active")
    @Operation(summary = "Get active locations", description = "Retrieves only active parking locations")
    public ResponseEntity<ApiResponse> getActiveLocations() {
        List<ParkingLocation> locations = parkingLocationService.getActiveLocations();
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Active locations retrieved successfully").data(locations).build()
        );
    }

    /** Retrieve a specific parking location by its ID. */
    @GetMapping("/{id}")
    @Operation(summary = "Get location by ID", description = "Retrieves a parking location by its ID")
    public ResponseEntity<ApiResponse> getLocationById(@PathVariable Long id) {
        ParkingLocation location = parkingLocationService.getLocationById(id);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Location retrieved successfully").data(location).build()
        );
    }

    /** Create a new parking location. */
    @PostMapping
    @Operation(summary = "Create location", description = "Creates a new parking location")
    public ResponseEntity<ApiResponse> createLocation(@Valid @RequestBody ParkingLocation location) {
        ParkingLocation created = parkingLocationService.createLocation(location);
        return new ResponseEntity<>(
                ApiResponse.builder().success(true).message("Location created successfully").data(created).build(),
                HttpStatus.CREATED
        );
    }

    /** Update an existing parking location. */
    @PutMapping("/{id}")
    @Operation(summary = "Update location", description = "Updates an existing parking location")
    public ResponseEntity<ApiResponse> updateLocation(@PathVariable Long id, @Valid @RequestBody ParkingLocation location) {
        ParkingLocation updated = parkingLocationService.updateLocation(id, location);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Location updated successfully").data(updated).build()
        );
    }

    /** Delete a parking location. */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete location", description = "Deletes a parking location")
    public ResponseEntity<ApiResponse> deleteLocation(@PathVariable Long id) {
        parkingLocationService.deleteLocation(id);
        return ResponseEntity.ok(
                ApiResponse.builder().success(true).message("Location deleted successfully").data(null).build()
        );
    }
}
