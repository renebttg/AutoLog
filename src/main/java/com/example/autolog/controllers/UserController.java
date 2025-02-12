package com.example.autolog.controllers;

import com.example.autolog.dtos.UserRecordDTO;
import com.example.autolog.dtos.UserResponseDTO;
import com.example.autolog.exceptions.AccessDeniedException;
import com.example.autolog.exceptions.UserNotFoundException;
import com.example.autolog.models.UserModel;
import com.example.autolog.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(@AuthenticationPrincipal UserModel user) {
        if (user == null) {
            throw new UserNotFoundException("Usuário não autenticado.");
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO(
                user.getIdUser(),
                user.getName(),
                user.getCnpj(),
                user.getEmail(),
                user.getPhone(),
                user.getNameWorkshop(),
                user.getAddressWorkshop(),
                user.getRole()
        );

        return ResponseEntity.ok(userResponseDTO);
    }


    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        authenticatedUser.getAuthorities().stream()
                .filter(auth -> auth.getAuthority().equals("ROLE_ADMIN"))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("Unauthorized access to user information."));

        List<UserModel> allUsers = Optional.of(userRepository.findAll())
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new UserNotFoundException("No users found in the database."));

        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") long id) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            UserModel user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            return ResponseEntity.status(HttpStatus.OK).body(user);

        } else {
            long authenticatedUserId = ((UserModel) authenticatedUser).getIdUser();
            if (authenticatedUserId != id) {
                throw new AccessDeniedException("Unauthorized access to user information.");
            }

            UserModel user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") long id, @RequestBody @Valid UserRecordDTO userRecordDto) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            UserModel userModel = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

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
                throw new AccessDeniedException("Unauthorized access to update user information.");
            }

            UserModel userModel = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

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
            UserModel userModel = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            userRepository.delete(userModel);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");

        } else {
            long authenticatedUserId = ((UserModel) authenticatedUser).getIdUser();
            if (authenticatedUserId != id) {
                throw new AccessDeniedException("Unauthorized access to delete user account.");
            }

            UserModel userModel = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            userRepository.delete(userModel);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
    }



}
