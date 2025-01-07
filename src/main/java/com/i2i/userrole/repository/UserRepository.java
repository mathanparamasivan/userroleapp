package com.i2i.userrole.repository;

import com.i2i.userrole.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for accessing and managing User entities in the database.
 * It provides methods for CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can define custom queries here if needed
}
