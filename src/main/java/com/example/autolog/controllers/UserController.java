package com.example.autolog.controllers;

import com.example.autolog.dtos.UserRecordDTO;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.UserRepository;
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
public class UserController {

    @Autowired
    UserRepository userRepository;

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
