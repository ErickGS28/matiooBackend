package com.fmdc.matioo.user.controller;

import com.fmdc.matioo.user.service.UserService;
import com.fmdc.matioo.user.model.ChangePasswordDTO;
import com.fmdc.matioo.user.model.ProfileDTO;
import com.fmdc.matioo.user.model.RecoveryDTO;
import com.fmdc.matioo.user.model.UserDTO;
import com.fmdc.matioo.utils.Message;
import com.fmdc.matioo.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> saveUser(@Validated(UserDTO.Register.class) @RequestBody UserDTO dto) {
        return userService.save(dto);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<Message> updateUser(@Validated(UserDTO.Modify.class) @RequestBody UserDTO dto) {
        return userService.update(dto);
    }

    @PutMapping("/update-profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INTERN', 'RESPONSIBLE')")
    public ResponseEntity<Message> updateProfile(
            @Validated(ProfileDTO.UpdateProfile.class) @RequestBody ProfileDTO dto) {
        return userService.updateProfile(dto);
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'INTERN', 'RESPONSIBLE')")
    public ResponseEntity<Message> changePassword(@Validated @RequestBody ChangePasswordDTO dto) {
        return userService.changePassword(dto);
    }

    @PostMapping("/send-recovery-code/{email}") 
        public ResponseEntity<Message> sendRecoveryCode(@PathVariable String email) {
        return userService.sendRecoveryCode(email);
    }

    @PostMapping("/verify-recovery-code")
    public ResponseEntity<Message> verifyRecoveryCode(@Validated @RequestBody RecoveryDTO dto) {
        return userService.verifyRecoveryCode(dto);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Message> resetPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String newPassword = payload.get("newPassword");
        if (email == null || newPassword == null) {
            return ResponseEntity.badRequest().body(new Message("Missing parameters", TypesResponse.ERROR));
        }
        return userService.resetPassword(email, newPassword);
    }

    @PutMapping("/change-status/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Message> changeStatus(@PathVariable Long id) {
        return userService.changeStatus(id);
    }

    @GetMapping("/active")
    public ResponseEntity<Message> getActiveUsers() {
        return userService.findActiveUsers();
    }

    // Obtener usuarios inactivos
    @GetMapping("/inactive")
    public ResponseEntity<Message> getInactiveUsers() {
        return userService.findInactiveUsers();
    }
}
