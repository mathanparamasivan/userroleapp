package com.i2i.userrole.repository;

import com.i2i.userrole.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository for accessing and managing User entities in the database.
 * It provides methods for CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE name LIKE CONCAT(?1, '%') AND is_present=true", nativeQuery = true)
    List<User> findByUsernameStartingWithPrefix(String prefix);

    @Query(value = "SELECT * FROM users WHERE id = ?1 AND is_present=true", nativeQuery = true)
    @Transactional
    Optional<User> findActiveUserById(Long id);

    @Query(value = "SELECT * FROM users WHERE is_present=true", nativeQuery = true)
    List<User> findAllActiveUsers();

    @Query(value = "UPDATE users SET is_present=false WHERE id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteUserById(Long id);

    @Query(value = "SELECT * FROM users WHERE name LIKE CONCAT(?1, '%') AND is_present=true order by name limit ?2 offset ?3", nativeQuery = true)
    List<User> findAllUsers(String startsWith, int limit, int skip);
}
