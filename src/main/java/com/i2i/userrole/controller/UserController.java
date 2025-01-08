package com.i2i.userrole.controller;

import com.i2i.userrole.dto.UserDTO;
import com.i2i.userrole.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users.
 * Provides endpoints to create, update, fetch, delete, and patch users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * POST /api/users : Create a new user.
     *
     * @param userDTO the user to create
     * @return the ResponseEntity with status 201 (Created)
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO result = userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * PUT /api/users : Update an existing user.
     *
     * @param id the id of the user to update
     * @param userDTO the updated user data
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO result = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/users/{id} : Get a user by id.
     *
     * @param id the id of the user to retrieve
     * @return the ResponseEntity with the user or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.notFound().build();
    }

    /**
     * GET /api/users : Get all users.
     *
     * @return the ResponseEntity with the list of all users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * GET /api/users/paginated : Get users with pagination.
     *
     * @param page the page number to fetch
     * @param size the number of items per page
     * @return the ResponseEntity with paginated user data
     */
    @GetMapping("/paginated")
    public ResponseEntity<List<UserDTO>> getAllUsersWithPagination(
            @RequestParam int page, @RequestParam int size) {
        List<UserDTO> users = userService.getAllUsersWithPagination(page, size);
        return ResponseEntity.ok(users);
    }

    /**
     * DELETE /api/users/{id} : Delete a user by id.
     *
     * @param id the id of the user to delete
     * @return the ResponseEntity with status 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * PATCH /api/users/{id} : Update partially an existing user.
     *
     * @param id the id of the user to patch
     * @param userDTO the partially updated user data
     * @return the ResponseEntity with the updated user data
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> patchUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO result = userService.patchUser(id, userDTO);
        return ResponseEntity.ok(result);
    }
}
