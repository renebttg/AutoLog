package com.example.autolog.application.usecase.auth;

import com.example.autolog.application.mapper.AuthMapper;
import com.example.autolog.domain.enums.UserRole;
import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.UserRepository;
import com.example.autolog.domain.repository.WorkshopRepository;
import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;
import com.example.autolog.presentation.request.user.RegisterUserRequest;
import com.example.autolog.presentation.response.user.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Rene
 */
@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final WorkshopRepository workshopRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    @Transactional
    public UserResponse execute(Long workshopId, RegisterUserRequest request) {
        WorkshopEntity workshop = workshopRepository.findById(workshopId)
                .orElseThrow(() -> new BusinessException("Workshop not found"));

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("A user with this email already exists");
        }

        if (request.role() == UserRole.OWNER) {
            throw new BusinessException("Cannot create another owner user");
        }

        UserEntity user = UserEntity.builder()
                .name(request.name().trim())
                .email(request.email().trim().toLowerCase())
                .password(passwordEncoder.encode(request.password()))
                .phone(normalizeNullable(request.phone()))
                .role(request.role())
                .active(true)
                .workshop(workshop)
                .build();

        return authMapper.toUserResponse(userRepository.save(user));
    }

    private String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
