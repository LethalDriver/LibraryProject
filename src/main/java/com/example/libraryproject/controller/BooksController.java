package com.example.libraryproject.controller;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.dto.BookDTO;
import com.example.libraryproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooksMatchingTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.getAllBooksMatchingTitle(title));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable String id) {
        return ResponseEntity.ok(bookService.getBookById(Long.parseLong(id)));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDTO));
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PutMapping
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.updateBook(bookDTO));
    }
}
