package com.i2i.userrole.dto;

import java.io.Serializable;

/**
 * Data Transfer Object for the User class.
 * Used to transfer user data between different layers of the application.
 * Contains fields for user ID, name, mobile number, and role.
 *
 * @author mathanpandi.p
 * @created 7/1/2025
 */
public class UserDTO implements Serializable {

    /**
     * The unique ID of the user.
     */
    private Long id;

    /**
     * The name of the user.
     */
    private String name;

    /**
     * The mobile number of the user.
     */
    private String mobileNumber;

    /**
     * The role of the user.
     */
    private String role;

    /**
     * Default constructor for UserDTO.
     * Initializes a new instance of UserDTO with no values set.
     */
    public UserDTO() {
    }

    /**
     * Parameterized constructor for UserDTO.
     * Initializes a new instance of UserDTO with the given values.
     *
     * @param id           the unique ID of the user
     * @param name         the name of the user
     * @param mobileNumber the mobile number of the user
     * @param role         the role of the user
     */
    public UserDTO(Long id, String name, String mobileNumber, String role) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.role = role;
    }

    /**
     * Gets the unique ID of the user.
     *
     * @return the user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique ID of the user.
     *
     * @param id the user ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the mobile number of the user.
     *
     * @return the user mobile number
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * Sets the mobile number of the user.
     *
     * @param mobileNumber the user mobile number
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Gets the role of the user.
     *
     * @return the user role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the user role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns a string representation of the UserDTO object.
     * Useful for debugging and logging purposes.
     *
     * @return a string representation of the UserDTO object
     */
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", role=" + role +
                '}';
    }
}
