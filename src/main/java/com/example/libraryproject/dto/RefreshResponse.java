package com.example.libraryproject.dto;

import lombok.Builder;

@Builder
public record RefreshResponse(
        String token
) {
}
