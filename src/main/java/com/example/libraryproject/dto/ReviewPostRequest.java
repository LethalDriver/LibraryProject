package com.example.libraryproject.dto;

public record ReviewPostRequest(
        Long bookId,
        String reviewContent,
        Integer rating
) {
}
