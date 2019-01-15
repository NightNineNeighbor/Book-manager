package com.group.artifact.domain;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.fixture.Fixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ReviewRepositoryTest extends AcceptanceTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByBookAndSlackId() {
        Book book = bookRepository.findByTitle("성공하는프로그래밍공부법").get();
        User user = userRepository.findBySlackId(Fixture.nnn.getSlackId()).get();

        Optional<Review> maybeReview = reviewRepository.findByBook_IdAndWriter_Id(book.getId(), user.getId());
        System.out.println(maybeReview.get());
        softly.assertThat(maybeReview.isPresent()).isTrue();
    }
}
