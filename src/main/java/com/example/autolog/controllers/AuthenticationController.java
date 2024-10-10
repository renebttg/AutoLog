package com.example.autolog.controllers;

import com.example.autolog.dtos.LoginResponseDTO;
import com.example.autolog.dtos.UserLoginDTO;
import com.example.autolog.dtos.UserRecordDTO;
import com.example.autolog.enums.UserRole;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.UserRepository;
import com.example.autolog.security.TokenService;
import com.example.autolog.services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rene
 */

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserLoginDTO userLoginRecordDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRecordDTO.email(), userLoginRecordDTO.password()));

            UserModel user = userRepository.findByEmail(userLoginRecordDTO.email());
            String token = tokenService.generateToken(user);
            LoginResponseDTO responseDTO = new LoginResponseDTO(token);
            return ResponseEntity.ok(responseDTO);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password combination");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UserRecordDTO userRecordDTO) {
        if (userRepository.findByEmail(userRecordDTO.email()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use");
        }

        if (userRepository.findByCnpj(userRecordDTO.cnpj()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ already in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRecordDTO.password());
        UserModel newUser = new UserModel();
        BeanUtils.copyProperties(userRecordDTO, newUser);
        newUser.setRole(UserRole.USER);
        newUser.setPassword(encryptedPassword);

        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestParam String email, HttpServletRequest request) {
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String resetToken = tokenService.passwordResetToken(user);

        String appUrlBase = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String resetUrl = appUrlBase + "/auth/reset-password?token=" + resetToken;

        emailService.sendResetPasswordEmail(user.getEmail(), resetUrl);

        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean isValidToken = tokenService.validateResetToken(token);
        if (!isValidToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }

        String email = tokenService.extractEmailFromToken(token);
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        return ResponseEntity.ok("Password has been reset successfully");
    }


}



