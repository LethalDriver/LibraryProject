package com.example.libraryproject.service;

import com.example.libraryproject.domain.BookDetails;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.libraryproject.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GoogleBooksService {
    private final RestTemplate restTemplate;
    @Value("${google.api.max-results}")
    private int maxResults;
    public List<Book> getBooks(String title) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=" + title + "&key=" + "&maxResults=" + maxResults;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        JSONObject jsonObject = new JSONObject(response.getBody());
        JSONArray items = jsonObject.getJSONArray("items");

        List<Book> books = new ArrayList<>();
        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            Book book = mapResponseElementToBook(item);
            books.add(book);
        }

        return books;
    }

    public Book mapResponseElementToBook(JSONObject jsonObject) {
        JSONObject volumeInfo = jsonObject.getJSONObject("volumeInfo");
        JSONArray authors = volumeInfo.optJSONArray("authors");
        String author = (authors != null && authors.length() != 0) ? authors.getString(0) : null;
        JSONObject industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers").getJSONObject(0);
        String isbn = industryIdentifiers.getString("identifier");

        String publisher = volumeInfo.optString("publisher", null);

        JSONArray categories = volumeInfo.optJSONArray("categories");
        String category = (categories != null && categories.length() != 0) ? categories.getString(0) : null;
        String summary = volumeInfo.optString("description", null);
        String coverImageUrl = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

        BookDetails bookDetails = BookDetails.builder()
                .genre(category)
                .summary(summary)
                .coverImageUrl(coverImageUrl)
                .build();

        Book book = Book.builder()
                .title(volumeInfo.getString("title"))
                .author(author)
                .isbn(isbn)
                .publisher(publisher)
                .availableCopies(new Random().nextInt(10) + 1)
                .bookDetails(bookDetails)
                .build();

        bookDetails.setBook(book);

        return book;
    }
}
