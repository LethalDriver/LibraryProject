package com.example.libraryproject.dto;

public record BookDTO(
        Long id,
        String title,
        String author,
        String isbn,
        String publisher,
        Integer availableCopies,
        BookDetailsDTO bookDetails
) {
}
