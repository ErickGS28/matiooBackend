package com.fmdc.matioo.user.model;

import com.fmdc.matioo.resources.Role;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class UserDTO {

    @NotNull(groups = {Modify.class, ChangeStatus.class}, message = "ID cannot be null.")
    private Long id;

    @NotBlank(groups = {Register.class, Modify.class}, message = "Full name cannot be empty.")
    @Size(max = 100, message = "Full name cannot exceed 100 characters.")
    private String fullName;

    @NotBlank(groups = {Register.class, Modify.class}, message = "Username cannot be empty.")
    @Size(max = 50, message = "Username cannot exceed 50 characters.")
    private String username;

    @NotBlank(groups = {Register.class}, message = "Password cannot be empty.")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters.")
    private String password;

    @NotBlank(groups = {Register.class, Modify.class}, message = "Email cannot be empty.")
    @Email(message = "Email must be valid.")
    @Size(max = 100, message = "Email cannot exceed 100 characters.")
    private String email;

    @NotBlank(groups = {Register.class, Modify.class}, message = "Location cannot be empty.")
    @Size(max = 150, message = "Location cannot exceed 150 characters.")
    private String location;

    @NotNull(groups = {Register.class, Modify.class, ChangeStatus.class}, message = "Role cannot be null.")
    private Role role;


    private String recoveryCode;
    private LocalDateTime codeExpiration;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getRecoveryCode() { return recoveryCode; }
    public void setRecoveryCode(String recoveryCode) { this.recoveryCode = recoveryCode; }
    public LocalDateTime getCodeExpiration() { return codeExpiration; }
    public void setCodeExpiration(LocalDateTime codeExpiration) { this.codeExpiration = codeExpiration; }

    // Validation groups
    public interface Register {}
    public interface Modify {}
    public interface ChangeStatus {}
}






