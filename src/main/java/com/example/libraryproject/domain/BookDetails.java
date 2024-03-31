package com.example.libraryproject.domain;

import jakarta.persistence.*;
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
    @Column(length = 2000)
    private String summary;
    private String coverImageUrl;
    @OneToOne
    private Book book;
}
