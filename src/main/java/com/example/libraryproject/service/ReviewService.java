package com.example.libraryproject.service;

import com.example.libraryproject.domain.User;
import com.example.libraryproject.dto.ReviewDTO;
import com.example.libraryproject.dto.ReviewPostRequest;
import com.example.libraryproject.mapper.ReviewMapper;
import com.example.libraryproject.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserService userService;

    public void deleteReview(Long id) {
        var currentUserId = userService.getCurrentUser().getId();
        var userRole = userService.getCurrentUser().getRole();
        var review = reviewRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Review with id " + id + " does not exist")
        );
        if (userRole == User.Role.READER || !Objects.equals(review.getUser().getId(), currentUserId)) {
            throw new IllegalStateException("User with id " + currentUserId + " cannot delete review with id " + id);
        }
        reviewRepository.deleteById(id);
    }

    public ReviewDTO addReview(ReviewPostRequest reviewPostRequest) {
        Long currentUserId = userService.getCurrentUser().getId();
        if (!Objects.equals(reviewPostRequest.userId(), currentUserId)) {
            throw new IllegalStateException("User with id " + reviewPostRequest.userId() + " cannot add review for user with id " + currentUserId);
        }
        var review = reviewRepository.save(reviewMapper.toEntity(reviewPostRequest));
        return reviewMapper.toDTO(review);
    }

    public ReviewDTO updateReview(ReviewPostRequest reviewPostRequest, Long reviewId) {
        var existingReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new EntityNotFoundException("Review with id " + reviewId + " does not exist")
        );
        Long currentUserId = userService.getCurrentUser().getId();
        if (!Objects.equals(existingReview.getUser().getId(), currentUserId)) {
            throw new IllegalStateException("User with id " + reviewPostRequest.userId() + " cannot update review with id " + reviewId);
        }
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
