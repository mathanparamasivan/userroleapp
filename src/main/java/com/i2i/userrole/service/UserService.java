package com.i2i.userrole.service;

import com.i2i.userrole.dto.UserDTO;
import com.i2i.userrole.entity.User;
import com.i2i.userrole.mapper.UserMapper;
import com.i2i.userrole.mapper.UserModelMapper;
import com.i2i.userrole.repository.UserRepository;
import com.i2i.userrole.repository.UserRoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserModelMapper userModelMapper;


    /**
     * Create or update a user.
     *
     * @param userDTO the user data transfer object to save
     * @return the saved user data transfer object
     */
     public UserDTO save(UserDTO userDTO) {
        User user = new User();
        userRoleRepository.findByRoleName(userDTO.getRole());
        userModelMapper.getUser(userDTO, user);
        user.setCreatedBy(getAutheticatedUserName());
        user.setCreated(Instant.now());
        user.setPresent(true);
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
        User user = userRepository.findActiveUserById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        userModelMapper.getUser(userDTO, user);
        user.setId(id);
        user.setUpdatedBy(getAutheticatedUserName());
        user.setUpdated(Instant.now());
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);  // Using save for both creation and updating
    }

    /**
     * Fetch a user by ID.
     *
     * @param id the ID of the user to fetch
     * @return the user data transfer object or null if not found
     */
    public UserDTO getUserById(Long id) {
       // return userMapper.toDto(entityManager.find(User.class,id));
        User user = userRepository.findActiveUserById(id).orElse(null);
        if(user == null)
            throw new RuntimeException("User Not Found");

        return userMapper.toDto(user);
    }

    /**
     * Fetch all users.
     *
     * @return list of all user data transfer objects
     */
    public List<UserDTO> getAllUsers(String prefix) {
        List<User> users = (prefix == null ||
                 prefix.isBlank() || !prefix.matches("[a-zA-Z]+")) ? userRepository.findAllActiveUsers():
        userRepository.findByUsernameStartingWithPrefix(prefix);

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
     * Fetch all users with pagination.
     *
     * @param startsWith the page number
     * @param skip the page number
     * @param limit the size of the page
     * @return paginated list of user data transfer objects
     */
    public List<UserDTO> getAllUsersWithPagination(String startsWith, int skip, int limit) {
        List<User> users = userRepository.findAllUsers(startsWith, limit, skip);
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
        User user = userRepository.findActiveUserById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        userRepository.deleteUserById(id);
    }

    /**
     * Partially update a user by ID.
     *
     * @param id the ID of the user to patch
     * @param name the user's name to patch
     * @return the patched user data transfer object
     */
    public UserDTO patchUser(Long id, String name) {
        User currentUser = userRepository.findActiveUserById(id).orElse(null);
        if (currentUser == null) {
            throw new RuntimeException("User not found");
        }

        currentUser.setName(name);
        currentUser.setUpdated(Instant.now());
        currentUser.setUpdatedBy(getAutheticatedUserName());
        return userMapper.toDto(userRepository.save(currentUser));  // Reusing save for patching
    }

    private String getAutheticatedUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
