package com.example.autolog.controllers;

import com.example.autolog.dtos.LoginResponseDTO;
import com.example.autolog.dtos.UserLoginDTO;
import com.example.autolog.dtos.UserRecordDTO;
import com.example.autolog.enums.UserRole;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.UserRepository;
import com.example.autolog.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Operation(summary = "User login", description = "Authenticate user credentials and generate a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login"),
            @ApiResponse(responseCode = "401", description = "Invalid email/password combination")
    })
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserLoginDTO userLoginRecordDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginRecordDTO.email(), userLoginRecordDTO.password()));

            UserModel user = userRepository.findByEmail(userLoginRecordDTO.email());
            String token = tokenService.generateToken(user);
            LoginResponseDTO responseDTO = new LoginResponseDTO(token);
            return ResponseEntity.ok(responseDTO);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password combination");
        }
    }

    @Operation(summary = "User registration", description = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Email or CNPJ already in use")
    })
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

        UserModel savedUser = userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

}



