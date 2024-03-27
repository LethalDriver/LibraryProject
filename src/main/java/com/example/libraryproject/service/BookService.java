package com.example.libraryproject.service;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.dto.BookDTO;
import com.example.libraryproject.mapper.BookMapper;
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
    private final BookMapper bookMapper;
    private List<Book> findBookByTitleInRepository (String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<BookDTO> getAllBooksMatchingTitle (String title) {
        List<Book> resultsFromRepo = findBookByTitleInRepository(title);
        if (resultsFromRepo.isEmpty()) {
            List<Book> resultsFromGoogle = googleBooksService.getBooks(title);
            bookRepository.saveAll(resultsFromGoogle);
            return resultsFromGoogle.stream().map(bookMapper::toDto).toList();
        }
        return resultsFromRepo.stream().map(bookMapper::toDto).toList();
    }

    public Optional<BookDTO> getBookById (Long id) {
        return bookRepository.findById(id).map(bookMapper::toDto);
    }

    public void addBook (BookDTO bookDTO) {
        bookRepository.save(bookMapper.toEntity(bookDTO));
    }

    public void updateBook (BookDTO bookDTO) {
        var updatedBook = bookMapper.toEntity(bookDTO);
        var existingBook = bookRepository.findById(updatedBook.getId()).orElseThrow(
                () -> new IllegalArgumentException("Book with id " + updatedBook.getId() + " does not exist")
        );
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPublisher(updatedBook.getPublisher());
        existingBook.setAvailableCopies(updatedBook.getAvailableCopies());
        bookRepository.save(existingBook);
    }
}
