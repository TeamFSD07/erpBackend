package com.springboot.web.controller;

import com.springboot.web.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ForgotPasswordController {

    @Autowired
    private PasswordResetService passwordResetService;

    @CrossOrigin(origins = "http://localhost:5173") // Allow requests from your React application
    @PostMapping("/send-reset-code")
    public ResponseEntity<String> sendResetCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        System.out.println("Received email: " + email);

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        boolean codeSent = passwordResetService.sendPasswordResetCode(email);

        if (codeSent) {
            return ResponseEntity.ok("Password reset code sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send reset code. Please try again.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        String newPassword = request.get("newPassword");

        System.out.println("Reset request for email: " + email + ", Code: " + code);

        if (email == null || email.isEmpty() || code == null || code.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("All fields are required");
        }

        boolean isReset = passwordResetService.resetPassword(email, code, newPassword);

        if (isReset) {
            return ResponseEntity.ok("Password has been reset successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code or reset failed.");
        }
    }
}
