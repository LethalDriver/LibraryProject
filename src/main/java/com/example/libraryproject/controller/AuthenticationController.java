package com.example.libraryproject.controller;

import com.example.libraryproject.dto.*;
import com.example.libraryproject.service.AuthenticationService;
import com.example.libraryproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody @Valid RegistrationRequest request) {
        return ResponseEntity.status(201).body(userService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> loginUser(@RequestBody AuthenticationRequest user) {
        return ResponseEntity.ok().body(authenticationService.authenticate(user));
    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshToken(@RequestBody TokensDTO request) {
        return ResponseEntity.ok(authenticationService.refresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody TokensDTO request) {
        authenticationService.logout(request);
        return ResponseEntity.noContent().build();
    }

}
