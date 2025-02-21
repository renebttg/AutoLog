package com.example.autolog.services;

import com.example.autolog.dtos.LoginResponseDTO;
import com.example.autolog.dtos.UserLoginDTO;
import com.example.autolog.dtos.UserRecordDTO;
import com.example.autolog.enums.TrustedAdminDomains;
import com.example.autolog.enums.UserRole;
import com.example.autolog.exceptions.AuthenticationException;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<Object> login(UserLoginDTO userLoginRecordDTO) {
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

    public ResponseEntity<Object> register(UserRecordDTO userRecordDTO) {
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

    public ResponseEntity<Object> registerAdmin(UserRecordDTO userRecordDTO) {
        if (!TrustedAdminDomains.isTrustedDomain(userRecordDTO.email())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized email domain for admin creation");
        }

        if (userRepository.findByEmail(userRecordDTO.email()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userRecordDTO.password());
        UserModel newAdmin = new UserModel();
        BeanUtils.copyProperties(userRecordDTO, newAdmin);
        newAdmin.setRole(UserRole.ADMIN);
        newAdmin.setPassword(encryptedPassword);

        userRepository.save(newAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully");
    }

    public ResponseEntity<Object> forgotPassword(String email, HttpServletRequest request) throws MessagingException, IOException {
        UserModel user = userRepository.findByEmail(email);
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
        UserModel user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(newPassword);
        user.setPassword(encryptedPassword);

        userRepository.save(user);

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
