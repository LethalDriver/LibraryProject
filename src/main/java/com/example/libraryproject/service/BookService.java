package com.example.libraryproject.service;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final GoogleBooksService googleBooksService;
    private List<Book> findBookByTitleInRepository (String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> getBooks(String title) {
        List<Book> resultsFromRepo = findBookByTitleInRepository(title);
        if (resultsFromRepo.isEmpty()) {
            List<Book> resultsFromGoogle = googleBooksService.getBooks(title);
            bookRepository.saveAll(resultsFromGoogle);
            return resultsFromGoogle;
        }
        return resultsFromRepo;
    }
}
