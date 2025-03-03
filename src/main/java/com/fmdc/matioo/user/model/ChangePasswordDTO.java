package com.fmdc.matioo.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangePasswordDTO {

    @NotNull(message = "User ID cannot be null.")
    private Long userId;

    @NotBlank(message = "New password cannot be empty.")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters.")
    private String newPassword;

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}