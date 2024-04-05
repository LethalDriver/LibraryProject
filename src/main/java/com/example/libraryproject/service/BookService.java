package com.example.libraryproject.service;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.dto.BookDTO;
import com.example.libraryproject.mapper.BookMapper;
import com.example.libraryproject.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Transactional
    public List<BookDTO> getAllBooksMatchingTitle (String title) {
        List<Book> resultsFromRepo = findBookByTitleInRepository(title);
        if (resultsFromRepo.isEmpty()) {
            List<Book> resultsFromGoogle = googleBooksService.getBooks(title);
            bookRepository.saveAll(resultsFromGoogle);
            return resultsFromGoogle.stream().map(bookMapper::toDto).toList();
        }
        return resultsFromRepo.stream().map(bookMapper::toDto).toList();
    }

    public BookDTO getBookById (Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " does not exist"));
    }

    @Transactional
    public BookDTO addBook (BookDTO bookDTO) {
        var addedBook = bookRepository.save(bookMapper.toEntity(bookDTO));
        return bookMapper.toDto(addedBook);
    }

    @Transactional
    public BookDTO updateBook (BookDTO bookDTO) {
        var updatedBook = bookMapper.toEntity(bookDTO);
        var existingBook = bookRepository.findById(updatedBook.getId()).orElseThrow(
                () -> new EntityNotFoundException("Book with id " + updatedBook.getId() + " does not exist")
        );
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPublisher(updatedBook.getPublisher());
        existingBook.setAvailableCopies(updatedBook.getAvailableCopies());
        existingBook.getBookDetails().setGenre(updatedBook.getBookDetails().getGenre());
        existingBook.getBookDetails().setSummary(updatedBook.getBookDetails().getSummary());
        existingBook.getBookDetails().setCoverImageUrl(updatedBook.getBookDetails().getCoverImageUrl());
        bookRepository.save(existingBook);
        return bookMapper.toDto(existingBook);
    }

    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }
}
