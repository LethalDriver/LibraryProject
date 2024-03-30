package com.example.libraryproject.controller;

import com.example.libraryproject.dto.RegistrationRequest;
import com.example.libraryproject.dto.UserDTO;
import com.example.libraryproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Secured("ROLE_LIBRARIAN")
    @GetMapping("/{id}")
    public UserDTO getUserInfo(@PathVariable String id) {
        return userService.getUserInfo(Long.parseLong(id));
    }

    @GetMapping
    public UserDTO getCurrentUserInfo() {
        Long currentUserId = userService.getCurrentUser().getId();
        return userService.getUserInfo(currentUserId);
    }

    @DeleteMapping
    public void deleteCurrentUser() {
        Long currentUserId = userService.getCurrentUser().getId();
        userService.deleteUser(currentUserId);
    }

    @Secured("ROLE_LIBRARIAN")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(Long.parseLong(id));
    }

    @Secured("ROLE_LIBRARIAN")
    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody RegistrationRequest registrationRequest) {
        return userService.updateUser(id, registrationRequest);
    }

    @PutMapping
    public UserDTO updateCurrentUser(@RequestBody RegistrationRequest registrationRequest) {
        Long currentUserId = userService.getCurrentUser().getId();
        return userService.updateUser(currentUserId, registrationRequest);
    }

}
