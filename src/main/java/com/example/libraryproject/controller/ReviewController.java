package com.example.libraryproject.controller;

import com.example.libraryproject.dto.ReviewDTO;
import com.example.libraryproject.service.ReviewService;
import com.example.libraryproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;
    @GetMapping("/book/{id}")
    public ResponseEntity<List<ReviewDTO>> getReviewsForABook(@PathVariable String id) {
        return ResponseEntity.ok(reviewService.getReviewsForABook(Long.parseLong(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable String id) {
        var review = reviewService.getReviewById(Long.parseLong(id));
        return ResponseEntity.ok(review);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable String id) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(Long.parseLong(id)));
    }


}
