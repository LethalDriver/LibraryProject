package com.example.libraryproject.dto;

public record ReviewDTO(Long id, String review, Integer rating, Long bookId, Long userId) {
}
