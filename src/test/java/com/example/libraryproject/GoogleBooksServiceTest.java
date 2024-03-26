package com.example.libraryproject;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.service.GoogleBooksService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GoogleBooksServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GoogleBooksService googleBooksService;

    @Test
    public void testGetBooks() throws IOException {
        String title = "Harry Potter";
        String responseString = new String(Files.readAllBytes(Paths.get("src/test/resources/example.json")));
        ResponseEntity<String> responseEntity = ResponseEntity.ok(responseString);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        List<Book> books = googleBooksService.getBooks(title);

        assertEquals(2, books.size());
    }
}