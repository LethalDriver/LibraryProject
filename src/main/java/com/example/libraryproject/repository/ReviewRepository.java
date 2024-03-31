package com.example.libraryproject.repository;

import com.example.libraryproject.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByBookId(Long bookId);

    List<Review> findAllByUserId(Long userId);
}
