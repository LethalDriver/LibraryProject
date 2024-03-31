package com.example.libraryproject.controller;

import com.example.libraryproject.service.AuthenticationService;
import com.example.libraryproject.dto.RegistrationRequest;
import com.example.libraryproject.dto.TokensDTO;
import com.example.libraryproject.service.UserService;
import com.example.libraryproject.dto.AuthenticationRequest;
import com.example.libraryproject.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public AuthenticationResponse registerUser(@RequestBody @Validated RegistrationRequest request) {
        userService.registerUser(request);
        return authenticationService.authenticate(new AuthenticationRequest(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/login")
    public AuthenticationResponse loginUser(@RequestBody AuthenticationRequest user) {
        return authenticationService.authenticate(user);
    }
    @PostMapping("/refresh")
    public AuthenticationResponse refreshToken(@RequestBody TokensDTO request) {
        return authenticationService.refresh(request);
    }

    @PostMapping("/logout")
    public void logout(@RequestBody TokensDTO request) {
        authenticationService.logout(request);
    }

}
