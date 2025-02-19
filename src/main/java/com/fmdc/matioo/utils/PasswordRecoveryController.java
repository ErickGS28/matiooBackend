package com.fmdc.matioo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-recovery")
public class PasswordRecoveryController {

    @Autowired
    private PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/request-code")
    public ResponseEntity<String> requestRecoveryCode(@RequestParam String email) {
        passwordRecoveryService.sendRecoveryCode(email);
        return ResponseEntity.ok("Código de recuperación enviado a " + email);
    }

    @PostMapping("/validate-code")
    public ResponseEntity<String> validateRecoveryCode(
            @RequestParam String email,
            @RequestParam String code) {
        boolean isValid = passwordRecoveryService.validateRecoveryCode(email, code);
        if (isValid) {
            return ResponseEntity.ok("Código válido. Puedes cambiar tu contraseña.");
        } else {
            return ResponseEntity.badRequest().body("Código inválido o expirado.");
        }
    }


    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam String email,
            @RequestParam String code,
            @RequestParam String newPassword) {
        boolean isValid = passwordRecoveryService.validateRecoveryCode(email, code);
        if (isValid) {
            // Lógica para cambiar la contraseña en la base de datos
            // userService.updatePassword(email, newPassword);
            return ResponseEntity.ok("Contraseña cambiada con éxito.");
        } else {
            return ResponseEntity.badRequest().body("Código inválido o expirado.");
        }
    }
}