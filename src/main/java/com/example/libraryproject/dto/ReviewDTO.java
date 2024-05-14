package com.example.libraryproject.dto;

public record ReviewDTO(Long id,
                        String review,
                        String reviewDate,
                        Integer rating,
                        Long bookId,
                        String username) {
}
