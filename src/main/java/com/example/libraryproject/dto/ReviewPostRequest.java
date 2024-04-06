package com.example.libraryproject.dto;

public record ReviewPostRequest(
        Long bookId,
        Long userId,
        String review,
        Integer rating
) {
}
