package com.example.libraryproject.domain;

import jakarta.persistence.Entity;
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
    private String genre;
    private String summary;
    private String coverImageUrl;
    @OneToOne
    private Book book;
}
