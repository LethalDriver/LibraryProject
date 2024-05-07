package com.example.libraryproject.service;

import com.example.libraryproject.domain.Book;
import com.example.libraryproject.domain.User;
import com.example.libraryproject.dto.ReviewDTO;
import com.example.libraryproject.dto.ReviewPostRequest;
import com.example.libraryproject.mapper.ReviewMapper;
import com.example.libraryproject.repository.BookRepository;
import com.example.libraryproject.repository.ReviewRepository;
import com.example.libraryproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;

    public void deleteReview(Long id) {
        var review = reviewRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Review with id " + id + " does not exist")
        );
        reviewRepository.deleteById(id);
    }

    public ReviewDTO addReview(ReviewPostRequest reviewPostRequest) {
        var user = userService.getCurrentUser();

        Book book = bookRepository.findById(reviewPostRequest.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        var review = reviewMapper.toEntity(reviewPostRequest);
        review.setBook(book);
        review.setUser(user);
        review.setReviewDate(LocalDate.now());
        review = reviewRepository.save(review);

        return reviewMapper.toDTO(review);
    }

    public ReviewDTO updateReview(ReviewPostRequest reviewPostRequest, Long reviewId) {
        var existingReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review with id " + reviewId + " does not exist")
        );
        var updatedReview = reviewMapper.toEntity(reviewPostRequest);
        existingReview.setReview(updatedReview.getReview());
        existingReview.setRating(updatedReview.getRating());
        var review = reviewRepository.save(existingReview);

        return reviewMapper.toDTO(review);
    }

    public List<ReviewDTO> getReviewsForABook(Long bookId) {
        return reviewRepository.findAllByBookId(bookId).stream()
                .map(reviewMapper::toDTO)
                .toList();
    }

    public ReviewDTO getReviewById(Long l) {
        return reviewRepository.findById(l)
                .map(reviewMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Review with id " + l + " does not exist"));
    }

    public List<ReviewDTO> getReviewsByUser(Long l) {
        return reviewRepository.findAllByUserId(l).stream()
                .map(reviewMapper::toDTO)
                .toList();
    }
}
