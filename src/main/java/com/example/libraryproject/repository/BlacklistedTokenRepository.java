package com.example.libraryproject.repository;

import com.example.libraryproject.domain.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    public boolean existsByToken(String token);
}
