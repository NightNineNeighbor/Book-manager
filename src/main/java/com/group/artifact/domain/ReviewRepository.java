package com.group.artifact.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByBook_IdAndWriter_Id(Long bookId, long writerId);

    Review findByReview(String review);
}
