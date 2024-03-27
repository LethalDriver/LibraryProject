package com.example.libraryproject;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.dto.BookDTO;
import com.example.libraryproject.repository.BookRepository;
import com.example.libraryproject.service.BookService;
import com.example.libraryproject.service.GoogleBooksService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private GoogleBooksService googleBooksService;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetBooksFromRepository() {
        String title = "Harry Potter";
        Book book = new Book();
        book.setTitle(title);
        when(bookRepository.findByTitleContainingIgnoreCase(eq(title))).thenReturn(Arrays.asList(book));

        List<BookDTO> books = bookService.getAllBooksMatchingTitle(title);

        assertEquals(1, books.size());
        assertEquals(title, books.get(0).title());
    }

    @Test
    public void testGetBooksFromGoogleBooksService() {
        String title = "Harry Potter";
        Book book = new Book();
        book.setTitle(title);
        when(bookRepository.findByTitleContainingIgnoreCase(eq(title))).thenReturn(Collections.emptyList());
        when(googleBooksService.getBooks(eq(title))).thenReturn(List.of(book));

        List<BookDTO> books = bookService.getAllBooksMatchingTitle(title);

        assertEquals(1, books.size());
        assertEquals(title, books.get(0).title());
    }

}
