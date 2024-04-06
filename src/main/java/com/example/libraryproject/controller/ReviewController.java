package com.example.libraryproject.controller;

import com.example.libraryproject.dto.ReviewDTO;
import com.example.libraryproject.dto.ReviewPostRequest;
import com.example.libraryproject.service.ReviewService;
import com.example.libraryproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping("/book/{id}")
    public ResponseEntity<List<ReviewDTO>> getReviewsForABook(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsForABook(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        var review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByUser(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getReviewsByUser(id));
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewPostRequest reviewPostRequest) {
        var review = reviewService.addReview(reviewPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody ReviewPostRequest reviewPostRequest, @PathVariable Long id) {
        return ResponseEntity.ok(reviewService.updateReview(reviewPostRequest, id));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_LIBRARIAN")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }


}
