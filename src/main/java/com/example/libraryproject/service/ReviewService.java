package com.example.libraryproject.service;

import com.example.libraryproject.dto.ReviewDTO;
import com.example.libraryproject.mapper.ReviewMapper;
import com.example.libraryproject.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public ReviewDTO addReview(ReviewDTO reviewDTO) {
        var review = reviewRepository.save(reviewMapper.toEntity(reviewDTO));
        return reviewMapper.toDTO(review);
    }

    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        var existingReview = reviewRepository.findById(reviewDTO.id()).orElseThrow(
                () -> new EntityNotFoundException("Review with id " + reviewDTO.id() + " does not exist")
        );
        var updatedReview = reviewMapper.toEntity(reviewDTO);
        existingReview.setReview(updatedReview.getReview());
        existingReview.setRating(updatedReview.getRating());
        existingReview.setReviewDate(updatedReview.getReviewDate());
        var review = reviewRepository.save(existingReview);

        return reviewMapper.toDTO(review);
    }

    public List<ReviewDTO> getReviewsForABook(Long bookId) {
        return reviewRepository.findAllByBookId(bookId).stream()
                .map(reviewMapper::toDTO)
                .toList();
    }
}
