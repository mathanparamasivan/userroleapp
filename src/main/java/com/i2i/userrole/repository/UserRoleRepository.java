package com.i2i.userrole.repository;

import com.i2i.userrole.entity.User;
import com.i2i.userrole.entity.UserRole;
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
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByRoleName(String roleName);
}
