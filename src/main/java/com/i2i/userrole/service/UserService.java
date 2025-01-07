package com.i2i.userrole.service;
import com.i2i.userrole.dto.UserDTO;
import com.i2i.userrole.entity.User;
import com.i2i.userrole.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing users.
 * Contains business logic for creating, updating, deleting, and fetching users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Create a new user.
     *
     * @param userDTO the user to create
     * @return the created UserDTO
     */
    public UserDTO createUser(UserDTO userDTO) {
        // Assuming a User entity exists and is mapped from the DTO
        User user = new User(userDTO);
        user = userRepository.save(user);
        return new UserDTO(user.getId(), user.getName(), user.getMobileNumber(), user.getRole());
    }

    /**
     * Update an existing user.
     *
     * @param id the id of the user to update
     * @param userDTO the updated user data
     * @return the updated UserDTO
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDTO.getName());
            user.setMobileNumber(userDTO.getMobileNumber());
            user.setRole(userDTO.getRole());
            user = userRepository.save(user);
            return new UserDTO(user.getId(), user.getName(), user.getMobileNumber(), user.getRole());
        }
        return null;  // Or throw an exception based on your needs
    }

    /**
     * Get a user by ID.
     *
     * @param id the id of the user
     * @return the UserDTO if found, else null
     */
    public UserDTO getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> new UserDTO(user.getId(), user.getName(), user.getMobileNumber(), user.getRole()))
                .orElse(null);
    }

    /**
     * Get all users.
     *
     * @return the list of all users
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getMobileNumber(), user.getRole()))
                .toList();
    }

    /**
     * Get users with pagination.
     *
     * @param page the page number
     * @param size the page size
     * @return the paginated list of users
     */
    public List<UserDTO> getAllUsersWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<User> users = userRepository.findAll(pageable).getContent();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getMobileNumber(), user.getRole()))
                .toList();
    }

    /**
     * Delete a user by ID.
     *
     * @param id the id of the user to delete
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Patch a user by ID.
     *
     * @param id the id of the user to patch
     * @param userDTO the partially updated user data
     * @return the patched UserDTO
     */
    public UserDTO patchUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userDTO.getName() != null) user.setName(userDTO.getName());
            if (userDTO.getMobileNumber() != null) user.setMobileNumber(userDTO.getMobileNumber());
            if (userDTO.getRole() != null) user.setRole(userDTO.getRole());
            user = userRepository.save(user);
            return new UserDTO(user.getId(), user.getName(), user.getMobileNumber(), user.getRole());
        }
        return null;  // Or throw an exception based on your needs
    }
}

