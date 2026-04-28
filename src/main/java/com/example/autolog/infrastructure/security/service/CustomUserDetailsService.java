package com.example.autolog.infrastructure.security.service;

import com.example.autolog.domain.exception.BusinessException;
import com.example.autolog.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new BusinessException("User not found or inactive"));
    }
}
