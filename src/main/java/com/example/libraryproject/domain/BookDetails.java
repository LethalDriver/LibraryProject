package com.example.libraryproject.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String genre;
    private String summary;
    private String coverImageUrl;
    @OneToOne
    private Book book;
}
