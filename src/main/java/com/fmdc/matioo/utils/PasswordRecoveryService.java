package com.fmdc.matioo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PasswordRecoveryService {

    @Autowired
    private EmailSender emailSender;

    private final Map<String, String> recoveryCodes = new HashMap<>();

    public void sendRecoveryCode(String email) {

        // Generar un código de recuperación único
        String recoveryCode = generateRecoveryCode();

        // Almacenar el código temporalmente
        recoveryCodes.put(email, recoveryCode);

        // Enviar el código por correo electrónico
        String subject = "Recuperación de contraseña";
        String text = "Tu código de recuperación es: " + recoveryCode;
        emailSender.sendSimpleMessage(email, subject, text);
    }

    public boolean validateRecoveryCode(String email, String code) {
        // Verificar si el código es válido
        String storedCode = recoveryCodes.get(email);
        return storedCode != null && storedCode.equals(code);
    }

    private String generateRecoveryCode() {

        // código aleatorio de 6 dígitos
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}