package com.example.libraryproject.service;


import com.example.libraryproject.dto.RefreshResponse;
import com.example.libraryproject.mapper.UserMapper;
import com.example.libraryproject.repository.BlacklistedTokenRepository;
import com.example.libraryproject.exception.TokenBlacklistedException;
import com.example.libraryproject.dto.TokensDTO;
import com.example.libraryproject.repository.UserRepository;
import com.example.libraryproject.domain.BlacklistedToken;
import com.example.libraryproject.dto.AuthenticationRequest;
import com.example.libraryproject.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BlacklistedTokenRepository blacklistedTokenRepository;
    private final UserMapper userMapper;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect password");
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        var userDto = userMapper.toDto(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken)
                .user(userDto)
                .build();
    }

    public RefreshResponse refresh(TokensDTO request) {
        var username = jwtService.extractUsername(request.getRefreshToken());
        var isTokenBlacklisted = blacklistedTokenRepository.existsByToken(request.getRefreshToken());
        if (isTokenBlacklisted) {
            throw new TokenBlacklistedException("Token is blacklisted");
        }
        var user = repository.findByUsername(username).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return RefreshResponse.builder()
                .token(jwtToken)
                .build();

    }

    public void logout(TokensDTO request) {
        blacklistedTokenRepository.save(new BlacklistedToken(request.getToken()));
        blacklistedTokenRepository.save(new BlacklistedToken(request.getRefreshToken()));
    }

}
