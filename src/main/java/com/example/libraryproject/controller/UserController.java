package com.example.libraryproject.controller;

import com.example.libraryproject.dto.RegistrationRequest;
import com.example.libraryproject.dto.UserDTO;
import com.example.libraryproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @Secured("ROLE_LIBRARIAN")
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @Secured("ROLE_LIBRARIAN")
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @Secured("ROLE_LIBRARIAN")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable Long id) {
        UserDTO user = userService.getUserInfo(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUserInfo() {
        UserDTO user = userService.getCurrentUserInfo();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCurrentUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_LIBRARIAN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Secured("ROLE_LIBRARIAN")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Valid RegistrationRequest registrationRequest) {
        UserDTO user = userService.updateUser(id, registrationRequest);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateCurrentUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        UserDTO user = userService.updateCurrentUser(registrationRequest);
        return ResponseEntity.ok(user);
    }
}