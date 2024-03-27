package com.example.libraryproject.service;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final GoogleBooksService googleBooksService;
    private List<Book> findBookByTitleInRepository (String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Book> getAllBooksMatchingTitle (String title) {
        List<Book> resultsFromRepo = findBookByTitleInRepository(title);
        if (resultsFromRepo.isEmpty()) {
            List<Book> resultsFromGoogle = googleBooksService.getBooks(title);
            bookRepository.saveAll(resultsFromGoogle);
            return resultsFromGoogle;
        }
        return resultsFromRepo;
    }

    public Optional<Book> getBookById (Long id) {
        return bookRepository.findById(id);
    }
}
