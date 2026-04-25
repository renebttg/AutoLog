package com.example.autolog.application.usecase.auth;

/**
 * @author Rene
 */
import com.example.autolog.application.mapper.AuthMapper;
import com.example.autolog.domain.enums.UserRole;
import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.UserRepository;
import com.example.autolog.domain.repository.WorkshopRepository;
import com.example.autolog.infrastructure.persistence.entity.UserEntity;
import com.example.autolog.infrastructure.persistence.entity.WorkshopEntity;
import com.example.autolog.presentation.request.workshop.RegisterWorkshopRequest;
import com.example.autolog.presentation.response.workshop.WorkshopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rene
 */
@Service
@RequiredArgsConstructor
public class RegisterWorkshopUseCase {

    private final WorkshopRepository workshopRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    @Transactional
    public WorkshopResponse execute(RegisterWorkshopRequest request) {

        if (workshopRepository.existsByCnpj(request.cnpj())) {
            throw new BusinessException("A workshop with this CNPJ already exists");
        }

        if (userRepository.existsByEmail(request.ownerEmail())) {
            throw new BusinessException("A user with this email already exists");
        }

        WorkshopEntity workshop = WorkshopEntity.builder()
                .name(request.workshopName().trim())
                .cnpj(request.cnpj().trim())
                .phone(normalizeNullable(request.phone()))
                .address(normalizeNullable(request.address()))
                .active(true)
                .build();

        WorkshopEntity savedWorkshop = workshopRepository.save(workshop);

        UserEntity owner = UserEntity.builder()
                .name(request.ownerName().trim())
                .email(request.ownerEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(request.ownerPassword()))
                .phone(normalizeNullable(request.ownerPhone()))
                .role(UserRole.OWNER)
                .active(true)
                .workshop(savedWorkshop)
                .build();

        userRepository.save(owner);

        return authMapper.toWorkshopResponse(savedWorkshop);
    }

    private String normalizeNullable(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}
