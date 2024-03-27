package com.example.libraryproject.controller;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.dto.BookDTO;
import com.example.libraryproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Optional<BookDTO> book = bookService.getBookById(Long.parseLong(id));
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
