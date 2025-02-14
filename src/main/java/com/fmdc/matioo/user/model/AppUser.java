package com.fmdc.matioo.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fmdc.matioo.item.model.Item;
import com.fmdc.matioo.resources.Role;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "location", length = 150, nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "recovery_code", length = 6)
    private String recoveryCode;

    @Column(name = "code_expiration")
    private LocalDateTime codeExpiration;

    @Column(name = "status", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean status = true;

    @OneToMany(mappedBy = "assignedTo")
    @JsonIgnore
    private List<Item> assignedItems;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Item> ownedItems;

    public AppUser() {}

    public AppUser(String fullName, String username, String password, String email, String location, Role role) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.location = location;
        this.role = role;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public LocalDateTime getCodeExpiration() {
        return codeExpiration;
    }

    public void setCodeExpiration(LocalDateTime codeExpiration) {
        this.codeExpiration = codeExpiration;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Item> getAssignedItems() {
        return assignedItems;
    }

    public void setAssignedItems(List<Item> assignedItems) {
        this.assignedItems = assignedItems;
    }

    public List<Item> getOwnedItems() {
        return ownedItems;
    }

    public void setOwnedItems(List<Item> ownedItems) {
        this.ownedItems = ownedItems;
    }
}
