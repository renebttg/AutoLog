package com.example.autolog.controllers;

import com.example.autolog.dtos.UserRecordDTO;
import com.example.autolog.dtos.UserResponseDTO;
import com.example.autolog.models.UserModel;
import com.example.autolog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author Rene
 */

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(@AuthenticationPrincipal UserModel user) {
       return userService.getAuthenticatedUser(user);
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") long id) {
        return userService.getOneUser(id);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") long id, @RequestBody @Valid UserRecordDTO userRecordDto) {
        return userService.updateUser(id, userRecordDto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") long id) {
        return userService.deleteUser(id);
    }

}
