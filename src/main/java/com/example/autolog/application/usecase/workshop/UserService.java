package com.example.autolog.application.usecase.workshop;

import com.example.autolog.presentation.request.RegisterWorkshopRequest;
import com.example.autolog.presentation.response.WorkshopResponse;
import com.example.autolog.domain.exception.AccessDeniedException;
import com.example.autolog.domain.exception.UserNotFoundException;
import com.example.autolog.infrastructure.persistance.entity.UserEntity;
import com.example.autolog.infrastructure.persistance.jpa.UserJpaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Rene
 */
@Service
public class UserService {

    @Autowired
    UserJpaRepository userJpaRepository;

    public ResponseEntity<WorkshopResponse> getAuthenticatedUser(@AuthenticationPrincipal UserEntity user) {
        if (user == null) {
            throw new UserNotFoundException("Usuário não autenticado.");
        }

        WorkshopResponse workshopResponse = WorkshopResponse.fromUserModel(user);

        return ResponseEntity.ok(workshopResponse);
    }

    public ResponseEntity<Object> getAllUsers() {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        authenticatedUser.getAuthorities().stream()
                .filter(auth -> auth.getAuthority().equals("ROLE_ADMIN"))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("Unauthorized access to user information."));

        List<UserEntity> allUsers = Optional.of(userJpaRepository.findAll())
                .filter(users -> !users.isEmpty())
                .orElseThrow(() -> new UserNotFoundException("No users found in the database."));

        return ResponseEntity.status(HttpStatus.OK).body(allUsers);
    }

    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") long id) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            UserEntity user = userJpaRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            return ResponseEntity.status(HttpStatus.OK).body(user);

        } else {
            long authenticatedUserId = ((UserEntity) authenticatedUser).getIdUser();
            if (authenticatedUserId != id) {
                throw new AccessDeniedException("Unauthorized access to user information.");
            }

            UserEntity user = userJpaRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") long id, @RequestBody @Valid RegisterWorkshopRequest registerWorkshopRequest) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            UserEntity userEntity = userJpaRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            BeanUtils.copyProperties(registerWorkshopRequest, userEntity);

            if (registerWorkshopRequest.password() != null && !registerWorkshopRequest.password().isEmpty()) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(registerWorkshopRequest.password());
                userEntity.setPassword(encryptedPassword);
            }

            UserEntity updatedUser = userJpaRepository.save(userEntity);
            return ResponseEntity.ok(updatedUser);

        } else {
            long authenticatedUserId = ((UserEntity) authenticatedUser).getIdUser();
            if (authenticatedUserId != id) {
                throw new AccessDeniedException("Unauthorized access to update user information.");
            }

            UserEntity userEntity = userJpaRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            BeanUtils.copyProperties(registerWorkshopRequest, userEntity);

            if (registerWorkshopRequest.password() != null && !registerWorkshopRequest.password().isEmpty()) {
                String encryptedPassword = new BCryptPasswordEncoder().encode(registerWorkshopRequest.password());
                userEntity.setPassword(encryptedPassword);
            }

            UserEntity updatedUser = userJpaRepository.save(userEntity);
            return ResponseEntity.ok(updatedUser);
        }
    }

    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") long id) {
        UserDetails authenticatedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            UserEntity userEntity = userJpaRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            userJpaRepository.delete(userEntity);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");

        } else {
            long authenticatedUserId = ((UserEntity) authenticatedUser).getIdUser();
            if (authenticatedUserId != id) {
                throw new AccessDeniedException("Unauthorized access to delete user account.");
            }

            UserEntity userEntity = userJpaRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

            userJpaRepository.delete(userEntity);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
    }

}
