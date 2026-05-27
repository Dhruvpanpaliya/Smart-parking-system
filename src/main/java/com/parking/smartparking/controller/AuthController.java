package com.parking.smartparking.controller;

import com.parking.smartparking.dto.ApiResponse;
import com.parking.smartparking.dto.LoginRequest;
import com.parking.smartparking.dto.RegisterRequest;
import com.parking.smartparking.entity.User;
import com.parking.smartparking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication operations.
 * Handles user registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration and login APIs")
public class AuthController {

    private final AuthService authService;

    /**
     * Register a new user in the system.
     *
     * @param request the registration details
     * @return the created user with 201 status
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided details")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = authService.register(request);
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .success(true)
                        .message("User registered successfully")
                        .data(user)
                        .build(),
                HttpStatus.CREATED
        );
    }

    /**
     * Authenticate a user with email and password.
     *
     * @param request the login credentials
     * @return the authenticated user details
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates user with email and password")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.login(request);
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .success(true)
                        .message("Login successful")
                        .data(user)
                        .build()
        );
    }
}
