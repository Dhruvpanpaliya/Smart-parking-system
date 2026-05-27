package com.parking.smartparking.service;

import com.parking.smartparking.entity.User;
import com.parking.smartparking.exception.ResourceNotFoundException;
import com.parking.smartparking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing user CRUD operations.
 * Provides methods to retrieve, update, and delete user records.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Retrieves all users from the system.
     *
     * @return list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the user ID
     * @return the User entity
     * @throws ResourceNotFoundException if no user is found with the given ID
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    /**
     * Updates an existing user's information.
     *
     * @param id   the ID of the user to update
     * @param user the user object containing updated fields
     * @return the updated User entity
     * @throws ResourceNotFoundException if no user is found with the given ID
     */
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setMobile(user.getMobile());
        existingUser.setPassword(user.getPassword());
        existingUser.setVehicleNumber(user.getVehicleNumber());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the ID of the user to delete
     * @throws ResourceNotFoundException if no user is found with the given ID
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }
}
