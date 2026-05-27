package com.parking.smartparking.service;

import com.parking.smartparking.dto.LoginRequest;
import com.parking.smartparking.dto.RegisterRequest;
import com.parking.smartparking.entity.User;
import com.parking.smartparking.exception.InvalidBookingException;
import com.parking.smartparking.exception.ResourceNotFoundException;
import com.parking.smartparking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service handling user authentication operations including registration and login.
 * Provides basic credential validation without JWT/token-based security.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    /**
     * Registers a new user in the system.
     * Validates that the email and mobile number are not already in use.
     *
     * @param request the registration request containing user details
     * @return the saved User entity
     * @throws InvalidBookingException if email or mobile already exists
     */
    public User register(RegisterRequest request) {
        // Check for duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new InvalidBookingException("Email already registered: " + request.getEmail());
        }

        // Check for duplicate mobile number
        if (userRepository.existsByMobile(request.getMobile())) {
            throw new InvalidBookingException("Mobile number already registered: " + request.getMobile());
        }

        // Build and persist the new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setPassword(request.getPassword());
        user.setVehicleNumber(request.getVehicleNumber());
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    /**
     * Authenticates a user with email and password.
     *
     * @param request the login request containing email and password
     * @return the authenticated User entity
     * @throws ResourceNotFoundException if no user found with given email
     * @throws InvalidBookingException   if password does not match
     */
    public User login(LoginRequest request) {
        // Look up user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with email: " + request.getEmail()));

        // Validate password
        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidBookingException("Invalid password");
        }

        return user;
    }
}
