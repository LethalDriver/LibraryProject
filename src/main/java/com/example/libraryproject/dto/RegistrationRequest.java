package com.example.libraryproject.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequest {
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Password must contain at least 8 characters, one uppercase letter, one lowercase letter, and one digit")
    private String password;
    @Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters")
    private String name;
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
}
