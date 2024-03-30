package com.example.libraryproject.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    @Nonnull
    private String username;
    @Nonnull
    private String email;
    @Nonnull
    private String password;
    @Nonnull
    private String name;
    @Nonnull
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Loan> loans;
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    public enum Role {
        READER, LIBRARIAN
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
