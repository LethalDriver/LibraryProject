package com.example.libraryproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Borrowing {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToMany
    private List<Book> books;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
