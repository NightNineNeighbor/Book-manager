package com.group.artifact.service;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.domain.*;
import com.group.artifact.fixture.Fixture;
import com.group.artifact.helper.DBInitializer;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class SlackServiceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(SlackServiceTest.class);
    @Autowired
    private SlackService slackService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DBInitializer dbInitializer;

    @Before
    public void setup(){
        dbInitializer.inite();
    }

    @Test
    public void readReviews(){
        userRepository.save(Fixture.otherUser);
        Review review = new Review("mmm의 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.otherUser.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);

        userRepository.save(Fixture.defaultUser);
        Review review1 = new Review("nnn의 리뷰", null, null);
        review1.setWriter(userRepository.findBySlackId(Fixture.defaultUser.getSlackId()).get());
        review1.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review1);

        ResponseEntity<String> response = slackService.readReview(Fixture.book.getTitle(), Fixture.channel);
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }

    @Test
    public void deleteReview(){
        userRepository.save(Fixture.defaultUser);
        Review review = new Review("새 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.defaultUser.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);

        int before = (int) reviewRepository.count();
        slackService.deleteReview(Fixture.book.getTitle(), Fixture.defaultUser.getSlackId(), Fixture.channel);
        int after = (int) reviewRepository.count();

        softly.assertThat(before - after).isEqualTo(1);
    }

    @Test
    public void createReview(){
        slackService.updateOrCreateReview(Fixture.book.getTitle(), "새로운 내용", Fixture.otherUser.getSlackId(),Fixture.channel);

        System.out.println(reviewRepository.findByReview("새로운 내용"));
        softly.assertThat(reviewRepository.findByReview("새로운 내용").getReview()).isEqualTo("새로운 내용");
    }

    @Test
    public void search(){
        List<Book> books = slackService.search("책");
        for (Book book : books) {
            System.out.println(book);
        }
    }
}