package com.example.libraryproject.dto;

public record LoanDTO(Long id, BookDTO book, UserDTO user, String loanDate, String returnDate, String dueDate, String status) {
}
