package com.i2i.userrole.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.i2i.userrole.dto.UserDTO;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Entity class for User.
 * Maps to the users table in the database and includes audit fields.
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)  // Enable auditing
@NamedQueries({
        @NamedQuery(name = "User.findByUsernameStartingWithG",
                query = "SELECT u FROM User u WHERE u.name LIKE 'G%'")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private com.i2i.userrole.entity.UserRole role;

    @Column(name = "is_present")
    private Boolean isPresent;

    // Audit Fields

    /**
     * Timestamp when the user was created.
     */
    @Column(name = "created")
    private Instant created;

    /**
     * Timestamp when the user was last updated.
     */
    @Column(name = "updated")
    private Instant updated;

    /**
     * User or system who created the user record.
     */
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    /**
     * User or system who last updated the user record.
     */
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    /**
     * Optimistic lock version field for concurrency control.
     */
    @Version
    @Column(name = "row_version")
    private Long rowVersion;

    // Default constructor
    public User() {
    }

    // Getters and Setters for Audit Fields

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Long rowVersion) {
        this.rowVersion = rowVersion;
    }

    // Getters and Setters for Other Fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", role=" + role +
                ", isPresent=" + isPresent +
                ", created=" + created +
                ", updated=" + updated +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", rowVersion=" + rowVersion +
                '}';
    }
}
