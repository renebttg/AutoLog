package com.example.autolog.presentation.controller;


import com.example.autolog.presentation.request.LoginRequest;
import com.example.autolog.presentation.request.workshop.RegisterWorkshopRequest;
import com.example.autolog.application.usecase.auth.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
/**
 * @author Rene
 */

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest userLoginRecordDTO) {
        return authenticationService.login(userLoginRecordDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterWorkshopRequest registerWorkshopRequest) {
        return authenticationService.register(registerWorkshopRequest);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<Object> registerAdmin(@Valid @RequestBody RegisterWorkshopRequest registerWorkshopRequest) {
        return authenticationService.registerAdmin(registerWorkshopRequest);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestParam String email, HttpServletRequest request) throws MessagingException, IOException {
        return authenticationService.forgotPassword(email, request);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        return authenticationService.resetPassword(token, newPassword);
    }

    @GetMapping("/reset-password")
    public ResponseEntity<Object> showResetPasswordPage(@RequestParam String token) {
        return authenticationService.showResetPasswordPage(token);
    }

}



