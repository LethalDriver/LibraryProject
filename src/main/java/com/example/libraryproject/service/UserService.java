package com.example.libraryproject.service;

import com.example.libraryproject.dto.RegistrationRequest;
import com.example.libraryproject.exception.UserAlreadyExistsException;
import com.example.libraryproject.repository.UserRepository;
import com.example.libraryproject.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails)principal).getUsername();
        }

        return null;
    }

    public User getCurrentUser() {
        return userRepository.findByEmail(getCurrentUserEmail()).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
    }

    public User registerUser(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        var user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(User.Role.READER)
                .build();

        return userRepository.save(user);
    }

}
