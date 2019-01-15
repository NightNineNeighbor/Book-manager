package com.group.artifact.service;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.domain.*;
import com.group.artifact.fixture.Fixture;
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

    @Test
    public void deleteReview(){bookRepository.save(Fixture.book);
        userRepository.save(Fixture.nnn);
        Review review = new Review("새 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.nnn.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);

        int before = (int) reviewRepository.count();
        slackService.deleteReview(Fixture.book.getTitle(), Fixture.nnn.getSlackId(), Fixture.channel);
        int after = (int) reviewRepository.count();

        softly.assertThat(before - after).isEqualTo(1);
    }

    @Test
    public void createReview(){
        slackService.updateOrCreateReview("성공하는프로그래밍공부법", "새로운 내용", Fixture.mmm.getSlackId(),Fixture.channel);

        System.out.println(reviewRepository.findByReview("새로운 내용"));
        softly.assertThat(reviewRepository.findByReview("새로운 내용").getReview()).isEqualTo("새로운 내용");
    }

    @Test
    public void search(){
        List<Book> books = slackService.search("성공하는 프로그래밍 공부법");
        for (Book book : books) {
            System.out.println(book);
        }
    }



//    @Test
    public void echo() {
        ResponseEntity<String> response = slackService.echo(Fixture.echo);
        System.out.println(response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }
}