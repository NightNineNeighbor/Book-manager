package com.group.artifact.domain;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.fixture.Fixture;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ReviewRepositoryTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ReviewRepositoryTest.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByBookAndSlackId() {
        bookRepository.save(Fixture.book);
        userRepository.save(Fixture.defaultUser);
        Book savedBook = bookRepository.findByTitle(Fixture.book.getTitle()).get();
        User savedUser = userRepository.findBySlackId(Fixture.defaultUser.getSlackId()).get();

        Review review = Fixture.reviewOne();
        review.setBook(savedBook);
        review.setWriter(savedUser);
        reviewRepository.save(review);

        Optional<Review> maybeReview = reviewRepository.findByBook_IdAndWriter_Id(savedBook.getId(), savedUser.getId());
        log.info("{}", maybeReview.get().toString());
        softly.assertThat(maybeReview.isPresent()).isTrue();
    }
}
