package com.example.libraryproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer availableCopies;
    @ManyToMany
    private List<Borrowing> borrowing;
}
