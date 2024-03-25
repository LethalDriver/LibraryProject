package com.example.libraryproject.service;

import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.libraryproject.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GoogleBooksService {
    public List<Book> getBooks(String title) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("https://www.googleapis.com/books/v1/volumes?q=" + title, String.class);

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
        JSONArray authors = volumeInfo.getJSONArray("authors");
        String author = !authors.isEmpty() ? authors.getString(0) : null;
        JSONObject industryIdentifiers = volumeInfo.getJSONArray("industryIdentifiers").getJSONObject(0);
        String isbn = industryIdentifiers.getString("identifier");

        return Book.builder()
                .title(volumeInfo.getString("title"))
                .author(author)
                .isbn(isbn)
                .publisher(volumeInfo.getString("publisher"))
                .availableCopies(new Random().nextInt(10) + 1)
                .build();
    }
}