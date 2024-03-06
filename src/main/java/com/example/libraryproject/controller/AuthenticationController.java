package com.example.libraryproject.controller;

import com.example.libraryproject.service.AuthenticationService;
import com.example.libraryproject.dto.RegistrationRequest;
import com.example.libraryproject.dto.TokensDTO;
import com.example.libraryproject.service.UserService;
import com.example.libraryproject.dto.AuthenticationRequest;
import com.example.libraryproject.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/auth/register")
    public AuthenticationResponse registerUser(@RequestBody RegistrationRequest request) {
        return authenticationService.authenticate(new AuthenticationRequest(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/auth/login")
    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest user) {
        return authenticationService.authenticate(user);
    }
    @PostMapping("/auth/refresh")
    public AuthenticationResponse refreshToken(@RequestBody TokensDTO request) {
        return authenticationService.refresh(request);
    }

    @PostMapping("/auth/logout")
    public void logout(@RequestBody TokensDTO request) {
        authenticationService.logout(request);
    }

}
