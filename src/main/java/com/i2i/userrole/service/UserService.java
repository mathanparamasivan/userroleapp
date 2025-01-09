package com.i2i.userrole.service;

import com.i2i.userrole.dto.UserDTO;
import com.i2i.userrole.entity.User;
import com.i2i.userrole.mapper.UserMapper;
import com.i2i.userrole.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 * Provides business logic for creating, updating, deleting, and fetching users.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private  EntityManager entityManager;

    /**
     * Create or update a user.
     *
     * @param userDTO the user data transfer object to save
     * @return the saved user data transfer object
     */
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    /**
     * Update a user based on ID.
     *
     * @param id the ID of the user to update
     * @param userDTO the updated user data
     * @return the updated user data transfer object
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userDTO.setId(id);
        return save(userDTO);  // Using save for both creation and updating
    }

    /**
     * Fetch a user by ID.
     *
     * @param id the ID of the user to fetch
     * @return the user data transfer object or null if not found
     */
    public UserDTO getUserById(Long id) {
        return userMapper.toDto(entityManager.find(User.class,id));
    }

    /**
     * Fetch all users.
     *
     * @return list of all user data transfer objects
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Fetch all users with pagination.
     *
     * @param page the page number
     * @param size the size of the page
     * @return paginated list of user data transfer objects
     */
    public List<UserDTO> getAllUsersWithPagination(int page, int size) {
        List<User> users = userRepository.findAll(PageRequest.of(page, size)).getContent();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Delete a user by ID.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Partially update a user by ID.
     *
     * @param id the ID of the user to patch
     * @param userDTO the user data to patch
     * @return the patched user data transfer object
     */
    public UserDTO patchUser(Long id, UserDTO userDTO) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userDTO.setId(id);
        return save(userDTO);  // Reusing save for patching
    }

    public List<UserDTO> getUsersStartingWithG() {
        return userRepository.findByUsernameStartingWithG().stream()
                .map(user -> userMapper.toDto(user)).collect(Collectors.toList());
    }

    public List<UserDTO> getUsersStartingWithH() {
        return userRepository.findByUsernameStartingWithH().stream()
                .map(user -> userMapper.toDto(user)).collect(Collectors.toList());
    }
}
