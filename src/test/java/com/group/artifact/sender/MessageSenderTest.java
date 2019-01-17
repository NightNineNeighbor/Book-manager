package com.group.artifact.sender;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.domain.Book;
import com.group.artifact.domain.Review;
import com.group.artifact.fixture.Fixture;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class MessageSenderTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(MessageSenderTest.class);

    @Autowired
    private MessageSender sender;

    @Test
    public void sendReview(){   //todo
        Review review = Fixture.reviewOne();
        review.setBook(Fixture.book);
        review.setWriter(Fixture.defaultUser);

        ResponseEntity<String> response = sender.sendReview(Fixture.channel,review);

        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }

    @Test
    public void review(){
        Review review = Fixture.reviewOne();
        review.setBook(Fixture.book);
        review.setWriter(Fixture.defaultUser);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        reviews.add(review);

        ResponseEntity<String> response = sender.review(reviews, Fixture.channel);

        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }

    @Test
    public void echo() {
        ResponseEntity<String> response = sender.echo(Fixture.echo);

        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }

    @Test
    public void bookInfo() {
        Book book = Fixture.book;
        ResponseEntity<String> response = sender.bookInfo(Fixture.book, Fixture.channel );

        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }
}