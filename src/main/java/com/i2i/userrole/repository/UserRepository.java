package com.i2i.userrole.repository;

import com.i2i.userrole.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for accessing and managing User entities in the database.
 * It provides methods for CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(name = "User.findByUsernameStartingWithG")
    List<User> findByUsernameStartingWithG();

    @Query(value = "SELECT * FROM users WHERE name LIKE 'H%'", nativeQuery = true)
    List<User> findByUsernameStartingWithH();
}
