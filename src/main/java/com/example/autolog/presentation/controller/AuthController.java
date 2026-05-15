package com.example.autolog.presentation.controller;

import com.example.autolog.application.usecase.auth.LoginUseCase;
import com.example.autolog.application.usecase.auth.RegisterWorkshopUseCase;
import com.example.autolog.presentation.request.auth.LoginRequest;
import com.example.autolog.presentation.request.workshop.RegisterWorkshopRequest;
import com.example.autolog.presentation.response.auth.LoginResponse;
import com.example.autolog.presentation.response.workshop.WorkshopResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rene
 */

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterWorkshopUseCase registerWorkshopUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse response = loginUseCase.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/workshops")
    public ResponseEntity<WorkshopResponse> registerWorkshop(
            @Valid @RequestBody RegisterWorkshopRequest request
    ) {
        WorkshopResponse response = registerWorkshopUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
