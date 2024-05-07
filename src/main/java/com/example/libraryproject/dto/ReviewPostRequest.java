package com.example.libraryproject.dto;

public record ReviewPostRequest(
        Long bookId,
        String review,
        Integer rating
) {
}
