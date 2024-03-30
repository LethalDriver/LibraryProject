package com.example.libraryproject.controller;

import com.example.libraryproject.dto.UserDTO;
import com.example.libraryproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
