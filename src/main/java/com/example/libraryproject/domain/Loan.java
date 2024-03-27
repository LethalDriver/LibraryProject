package com.example.libraryproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private List<Book> books;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private LocalDate dueDate;
}
