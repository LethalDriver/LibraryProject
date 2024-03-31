package com.example.libraryproject.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private String review;
    private Integer rating;
    private LocalDate reviewDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
