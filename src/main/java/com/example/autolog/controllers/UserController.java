package com.example.autolog.controllers;

import com.example.autolog.dtos.UserRecordDTO;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Rene
 */

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User list retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to user information")
    })
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            List<UserModel> allUsers = userRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(allUsers);

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access to user information.");
        }
    }

    @Operation(summary = "Get one user", description = "Retrieve details of a single user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to user information"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") long id) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            Optional<UserModel> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());

        } else {
            long authenticatedUserId = ((UserModel) authenticatedUser).getIdUser();
            if (authenticatedUserId != id) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access to user information.");
            }

            Optional<UserModel> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
        }
    }

    @Operation(summary = "Update user", description = "Update details of an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to update user information"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") long id, @RequestBody @Valid UserRecordDTO userRecordDto) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            Optional<UserModel> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            UserModel userModel = userOptional.get();
            BeanUtils.copyProperties(userRecordDto, userModel);

            if (userRecordDto.password() != null && !userRecordDto.password().isEmpty()) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(userRecordDto.password());
                userModel.setPassword(encryptedPassword);
            }
            UserModel updatedUser = userRepository.save(userModel);
            return ResponseEntity.ok(updatedUser);

        } else {
            long authenticatedUserId = ((UserModel) authenticatedUser).getIdUser();
            if (authenticatedUserId != id) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access to update user information.");
            }
            Optional<UserModel> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            UserModel userModel = userOptional.get();
            BeanUtils.copyProperties(userRecordDto, userModel);
            if (userRecordDto.password() != null && !userRecordDto.password().isEmpty()) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(userRecordDto.password());
                userModel.setPassword(encryptedPassword);
            }
            UserModel updatedUser = userRepository.save(userModel);
            return ResponseEntity.ok(updatedUser);
        }
    }

    @Operation(summary = "Delete user", description = "Delete a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access to delete user account"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") long id) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            Optional<UserModel> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            userRepository.delete(userOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        } else {
            long authenticatedUserId = ((UserModel) authenticatedUser).getIdUser();
            if (authenticatedUserId != id) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access to delete user account.");
            }
            Optional<UserModel> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
            userRepository.delete(userOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
    }


}
