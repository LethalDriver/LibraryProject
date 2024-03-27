package com.example.libraryproject.controller;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    @GetMapping
    public ResponseEntity<List<Book>> getBooksMatchingTitle(@RequestParam String title) {
        return ResponseEntity.ok(bookService.getAllBooksMatchingTitle(title));
    }
}
