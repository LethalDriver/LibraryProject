package com.example.libraryproject.service;

import com.example.libraryproject.dto.AuthenticationRequest;
import com.example.libraryproject.dto.AuthenticationResponse;
import com.example.libraryproject.dto.RegistrationRequest;
import com.example.libraryproject.dto.UserDTO;
import com.example.libraryproject.exception.UserAlreadyExistsException;
import com.example.libraryproject.mapper.UserMapper;
import com.example.libraryproject.repository.UserRepository;
import com.example.libraryproject.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByUsername(username).orElseThrow(
                    () -> new EntityNotFoundException("User not found")
            );
        }

        return null;
    }

    public AuthenticationResponse registerUser(RegistrationRequest request) {
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

        userRepository.save(user);

        return authenticationService.authenticate(
                new AuthenticationRequest(request.getEmail(), request.getPassword())
        );
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteCurrentUser() {
        Long currentUserId = getCurrentUser().getId();
        deleteUser(currentUserId);
    }

    public UserDTO updateUser(Long id, RegistrationRequest request) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        var updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    public UserDTO updateCurrentUser(RegistrationRequest request) {
        Long currentUserId = getCurrentUser().getId();
        return updateUser(currentUserId, request);
    }

    public UserDTO getUserInfo(long l) {
        return userMapper.toDto(getUserById(l));
    }

    public UserDTO getCurrentUserInfo() {
        return userMapper.toDto(getCurrentUser());
    }
}
