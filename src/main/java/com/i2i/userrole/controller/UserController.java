package com.i2i.userrole.controller;

import com.i2i.userrole.dto.UserDTO;
import com.i2i.userrole.entity.User;
import com.i2i.userrole.mapper.UserMapper;
import com.i2i.userrole.repository.UserRepository;
import com.i2i.userrole.service.UserService;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users.
 * Provides endpoints to create, update, fetch, delete, and patch users.
 */
@RestController
@RequestMapping("/api/users")
@RefreshScope
public class UserController {

    @Value("${latest-prop}")
    private String latestProp;

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper mapper;

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

    @PostMapping("/trans")
    public ResponseEntity<UserDTO> createAndUpdateUser(@RequestBody UserDTO userDTO) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(def);
        UserDTO result = null;
        try {

            User user = userRepository.save(mapper.toEntity(userDTO));
            if (user.getName().equals("Ganesh"))
                throw new RuntimeException();

            user.setName(user.getName() + "updated");
            result = mapper.toDto(userRepository.save(user));

            if (user.getName().equals("Rakeshupdated"))
                throw new RuntimeException();

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            return null;
        }

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
        System.out.println("Latest prop:"  + latestProp);
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/G")
    public ResponseEntity<List<UserDTO>> getAllGUsers() {
        System.out.println("Latest prop:"  + latestProp);
        List<UserDTO> users = userService.getUsersStartingWithG();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/H")
    public ResponseEntity<List<UserDTO>> getAllHUsers() {
        System.out.println("Latest prop:"  + latestProp);
        List<UserDTO> users = userService.getUsersStartingWithH();
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
