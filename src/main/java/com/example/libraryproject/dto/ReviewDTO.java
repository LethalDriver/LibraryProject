package com.example.libraryproject.dto;

public record ReviewDTO(Long id,
                        String reviewContent,
                        String reviewDate,
                        Integer rating,
                        Long bookId,
                        String username) {
}
