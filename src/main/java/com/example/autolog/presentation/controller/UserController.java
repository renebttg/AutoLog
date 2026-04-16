package com.example.autolog.presentation.controller;

import com.example.autolog.presentation.request.RegisterWorkshopRequest;
import com.example.autolog.presentation.response.WorkshopResponse;
import com.example.autolog.infrastructure.persistance.entity.UserEntity;
import com.example.autolog.infrastructure.service.UserService;
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
    public ResponseEntity<WorkshopResponse> getAuthenticatedUser(@AuthenticationPrincipal UserEntity user) {
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
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") long id, @RequestBody @Valid RegisterWorkshopRequest registerWorkshopRequest) {
        return userService.updateUser(id, registerWorkshopRequest);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") long id) {
        return userService.deleteUser(id);
    }

}
