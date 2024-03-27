package com.example.libraryproject.dto;

public record LoanDTO(Long id, Long bookId, Long userId, String loanDate, String returnDate, String dueDate, String status) {
}
