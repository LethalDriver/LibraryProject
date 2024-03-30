package com.example.libraryproject.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private Long id;
    private String review;
    private Integer rating;
    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}
