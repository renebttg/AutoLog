package com.example.autolog.application.usecase.auth;

import com.example.autolog.application.mapper.AuthMapper;
import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.UserRepository;
import com.example.autolog.domain.service.TokenService;
import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import com.example.autolog.presentation.request.auth.LoginRequest;
import com.example.autolog.presentation.response.auth.LoginResponse;
import com.example.autolog.presentation.response.auth.UserSummaryResponse;
import com.example.autolog.presentation.response.auth.WorkshopSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Rene
 */
@Service
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthMapper authMapper;

    public LoginResponse execute(LoginRequest request) {

        UserEntity user = userRepository.findByEmailAndActiveTrue(request.email().trim().toLowerCase())
                .orElseThrow(() -> new BusinessException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Invalid email or password");
        }

        String token = tokenService.generateToken(user);

        return new LoginResponse(
                token,
                authMapper.toUserSummaryResponse(user),
                authMapper.toWorkshopSummaryResponse(user.getWorkshop())
        );
    }
}
