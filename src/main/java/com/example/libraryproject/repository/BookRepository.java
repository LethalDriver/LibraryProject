package com.example.libraryproject.repository;

import com.example.libraryproject.domain.Book;
import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BookRepository extends PagingAndSortingRepository<Book, Long>, CrudRepository<Book, Long>{
    List<Book> findByTitleContainingIgnoreCase(String title);
    @Nonnull
    List<Book> findAll();
}
