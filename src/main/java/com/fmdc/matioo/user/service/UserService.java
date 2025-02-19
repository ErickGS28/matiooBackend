package com.fmdc.matioo.user.service;

import com.fmdc.matioo.user.model.*;
import com.fmdc.matioo.user.repository.AppUserRepository;
import com.fmdc.matioo.utils.EmailSender;
import com.fmdc.matioo.utils.Message;
import com.fmdc.matioo.utils.TypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSender emailSender;

    @Autowired
    public UserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }

    // GET ALL USERS
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findAll() {
        return new ResponseEntity<>(new Message(userRepository.findAll(), "User list", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // FIND USER BY ID
    @Transactional(readOnly = true)
    public ResponseEntity<Message> findById(Long id) {
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(new Message(user, "User found", TypesResponse.SUCCESS), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new Message("User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND));
    }

    // CREATE USER
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> save(UserDTO dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            return new ResponseEntity<>(new Message("Email is already in use", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            return new ResponseEntity<>(new Message("Username is already in use", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }
    
        AppUser user = new AppUser(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getEmail(),
                dto.getLocation(),
                dto.getRole()
        );
        user.setStatus(true);
    
        userRepository.save(user);
        return new ResponseEntity<>(new Message(user, "User created successfully", TypesResponse.SUCCESS), HttpStatus.CREATED);
    }
    

    // UPDATE USER PROFILE
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> updateProfile(ProfileDTO dto) {
        Optional<AppUser> optionalUser = userRepository.findById(dto.getId());
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(new Message("User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        AppUser user = optionalUser.get();
        user.setFullName(dto.getFullName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setLocation(dto.getLocation());

        userRepository.save(user);
        return new ResponseEntity<>(new Message(user, "Profile updated successfully", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // CHANGE PASSWORD
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changePassword(ChangePasswordDTO dto) {
        Optional<AppUser> optionalUser = userRepository.findById(dto.getUserId());
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(new Message("User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        AppUser user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(user);
        return new ResponseEntity<>(new Message("Password updated successfully", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // SEND RECOVERY CODE
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> sendRecoveryCode(String email) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(new Message("No user found with this email", TypesResponse.WARNING), HttpStatus.NOT_FOUND);
        }

        AppUser user = optionalUser.get();
        String code = String.format("%06d", new Random().nextInt(1000000));
        user.setRecoveryCode(code);
        user.setCodeExpiration(LocalDateTime.now().plusMinutes(10));

        userRepository.save(user);

        emailSender.sendSimpleMessage(user.getEmail(), "Recuperacion de contraseña", "Su código de recuperación es: " + code);

        return new ResponseEntity<>(new Message("Recovery code sent successfully", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // VERIFY RECOVERY CODE
    @Transactional(readOnly = true)
    public ResponseEntity<Message> verifyRecoveryCode(RecoveryDTO dto) {
        Optional<AppUser> optionalUser = userRepository.findFirstByRecoveryCode(dto.getRecoveryCode());
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(new Message("Invalid or expired recovery code", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        AppUser user = optionalUser.get();
        if (user.getCodeExpiration().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(new Message("Recovery code has expired", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Message("Recovery code verified successfully", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // RESET PASSWORD AFTER VERIFICATION
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> resetPassword(String email, String newPassword) {
        Optional<AppUser> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(new Message("User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        AppUser user = optionalUser.get();
        if (user.getRecoveryCode() == null || user.getCodeExpiration().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(new Message("Recovery code is invalid or expired", TypesResponse.WARNING), HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRecoveryCode(null);
        user.setCodeExpiration(null);

        userRepository.save(user);
        return new ResponseEntity<>(new Message("Password reset successfully", TypesResponse.SUCCESS), HttpStatus.OK);
    }

    // CHANGE USER STATUS (ENABLE/DISABLE)
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Message> changeStatus(Long id) {
        Optional<AppUser> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(new Message("User not found", TypesResponse.ERROR), HttpStatus.NOT_FOUND);
        }

        AppUser user = optionalUser.get();
        user.setStatus(!user.isStatus());

        userRepository.save(user);
        return new ResponseEntity<>(new Message(user, "User status updated", TypesResponse.SUCCESS), HttpStatus.OK);
    }
}
