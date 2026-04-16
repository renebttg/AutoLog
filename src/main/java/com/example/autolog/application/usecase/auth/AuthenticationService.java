package com.example.autolog.application.usecase.auth;

import com.example.autolog.infrastructure.mail.EmailService;
import com.example.autolog.infrastructure.security.token.TokenService;
import com.example.autolog.presentation.response.LoginResponse;
import com.example.autolog.presentation.request.UserLoginRquest;
import com.example.autolog.presentation.request.RegisterWorkshopRequest;
import com.example.autolog.domain.enums.TrustedAdminDomains;
import com.example.autolog.domain.enums.UserRole;
import com.example.autolog.domain.exception.AuthenticationException;
import com.example.autolog.infrastructure.persistance.entity.UserEntity;
import com.example.autolog.infrastructure.persistance.jpa.UserJpaRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Rene
 */
@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<Object> login(UserLoginRquest userLoginRecordDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRecordDTO.email(), userLoginRecordDTO.password()));

            UserEntity user = userJpaRepository.findByEmail(userLoginRecordDTO.email());
            String token = tokenService.generateToken(user);
            LoginResponse responseDTO = new LoginResponse(token);
            return ResponseEntity.ok(responseDTO);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password combination");
        }
    }

    public ResponseEntity<Object> register(RegisterWorkshopRequest registerWorkshopRequest) {
        if (userJpaRepository.findByEmail(registerWorkshopRequest.email()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use");
        }

        if (userJpaRepository.findByCnpj(registerWorkshopRequest.cnpj()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ already in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerWorkshopRequest.password());
        UserEntity newUser = new UserEntity();
        BeanUtils.copyProperties(registerWorkshopRequest, newUser);
        newUser.setRole(UserRole.USER);
        newUser.setPassword(encryptedPassword);

        userJpaRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    public ResponseEntity<Object> registerAdmin(RegisterWorkshopRequest registerWorkshopRequest) {
        if (!TrustedAdminDomains.isTrustedDomain(registerWorkshopRequest.email())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized email domain for admin creation");
        }

        if (userJpaRepository.findByEmail(registerWorkshopRequest.email()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerWorkshopRequest.password());
        UserEntity newAdmin = new UserEntity();
        BeanUtils.copyProperties(registerWorkshopRequest, newAdmin);
        newAdmin.setRole(UserRole.ADMIN);
        newAdmin.setPassword(encryptedPassword);

        userJpaRepository.save(newAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully");
    }

    public ResponseEntity<Object> forgotPassword(String email, HttpServletRequest request) throws MessagingException, IOException {
        UserEntity user = userJpaRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String resetToken = tokenService.passwordResetToken(user);

        String resetUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "")
                + "/auth/reset-password?token=" + resetToken;

        emailService.sendResetPasswordEmail(user.getEmail(), resetUrl);

        return ResponseEntity.ok("Password reset email sent");
    }

    public ResponseEntity<Object> resetPassword(String token, String newPassword) {
        boolean isValidToken = tokenService.validateResetToken(token);
        if (!isValidToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }

        String email = tokenService.extractEmailFromToken(token);
        UserEntity user = userJpaRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encryptedPassword);

        userJpaRepository.save(user);

        return ResponseEntity.ok("Password has been reset successfully");
    }

    public ResponseEntity<Object> showResetPasswordPage(String token) {
        boolean isValidToken = tokenService.validateResetToken(token);

        if (!isValidToken) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }

        return ResponseEntity.ok("Token is valid, please enter your new password.");
    }
}
