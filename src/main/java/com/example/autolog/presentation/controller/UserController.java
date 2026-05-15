package com.example.autolog.presentation.controller;

import com.example.autolog.application.usecase.auth.RegisterUserUseCase;
import com.example.autolog.application.usecase.user.GetCurrentUserUseCase;
import com.example.autolog.application.usecase.user.ListWorkshopUsersUseCase;
import com.example.autolog.presentation.request.user.RegisterUserRequest;
import com.example.autolog.presentation.response.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rene
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final ListWorkshopUsersUseCase listWorkshopUsersUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    @PostMapping
    public ResponseEntity<UserResponse> registerUser(
            @Valid @RequestBody RegisterUserRequest request
    ) {
        UserResponse response = registerUserUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> listUsers() {
        List<UserResponse> response = listWorkshopUsersUseCase.execute();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        UserResponse response = getCurrentUserUseCase.execute();
        return ResponseEntity.ok(response);
    }
}
