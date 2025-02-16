package com.fmdc.matioo.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProfileDTO {

    @NotNull(groups = {UpdateProfile.class}, message = "ID cannot be null.")
    private Long id;

    @NotBlank(groups = {UpdateProfile.class}, message = "Full name cannot be empty.")
    @Size(max = 100, message = "Full name cannot exceed 100 characters.")
    private String fullName;

    @NotBlank(groups = {UpdateProfile.class}, message = "Username cannot be empty.")
    @Size(max = 50, message = "Username cannot exceed 50 characters.")
    private String username;

    @NotBlank(groups = {UpdateProfile.class}, message = "Email cannot be empty.")
    @Email(message = "Email must be valid.")
    @Size(max = 100, message = "Email cannot exceed 100 characters.")
    private String email;

    @NotBlank(groups = {UpdateProfile.class}, message = "Location cannot be empty.")
    @Size(max = 150, message = "Location cannot exceed 150 characters.")
    private String location;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    // Validation groups
    public interface UpdateProfile {}
}