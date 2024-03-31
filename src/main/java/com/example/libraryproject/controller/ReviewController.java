package com.example.libraryproject.controller;

import com.example.libraryproject.dto.ReviewDTO;
import com.example.libraryproject.service.ReviewService;
import com.example.libraryproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
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

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        var review = reviewService.addReview(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @PutMapping
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody ReviewDTO reviewDTO) {
        var review = reviewService.updateReview(reviewDTO);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_LIBRARIAN")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        reviewService.deleteReview(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/user")
    public ResponseEntity<Void> deleteReviewIfBelongsToUser(@PathVariable String id) {
        reviewService.deleteReviewIfBelongsToUser(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }


}
