package com.example.autolog.application.usecase.user;

import com.example.autolog.application.mapper.AuthMapper;
import com.example.autolog.domain.repository.UserRepository;
import com.example.autolog.infrastructure.security.service.AuthenticatedUserService;
import com.example.autolog.presentation.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListWorkshopUsersUseCase {

    private final UserRepository userRepository;
    private final AuthenticatedUserService authenticatedUserService;
    private final AuthMapper authMapper;

    public List<UserResponse> execute() {
        Long workshopId = authenticatedUserService.getCurrentWorkshopId();

        return userRepository.findAllByWorkshopId(workshopId)
                .stream()
                .map(authMapper::toUserResponse)
                .toList();
    }
}