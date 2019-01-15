package com.group.artifact.web;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.domain.*;
import com.group.artifact.fixture.Fixture;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public class ApiSlackControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiSlackControllerTest.class);
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void before() {
        bookRepository.deleteByTitle(Fixture.book.getTitle());
        bookRepository.deleteByTitle(Fixture.book2.getTitle());
        userRepository.deleteBySlackId(Fixture.mmm.getSlackId());
        userRepository.deleteBySlackId(Fixture.nnn.getSlackId());
        bookRepository.save(Fixture.book);
        bookRepository.save(Fixture.book2);
        userRepository.save(Fixture.nnn);
        userRepository.save(Fixture.mmm);

//        Fixture.review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
//        Fixture.review.setWriter(userRepository.findBySlackId(Fixture.nnn.getSlackId()).get());
//        Fixture.review2.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
//        Fixture.review2.setWriter(userRepository.findBySlackId(Fixture.mmm.getSlackId()).get());
//
//        reviewRepository.save(Fixture.review);
//        reviewRepository.save(Fixture.review2);
    }


    @Test
    public void createReviewManyBook() {
        ResponseEntity<String> response = postMessage("번째 책 !리뷰 !등록", Fixture.mmm.getSlackId());
        softly.assertThat(response.getBody()).contains("MANY BOOK");

        ResponseEntity<String> response1 = postMessage("1", Fixture.mmm.getSlackId());
        softly.assertThat(response1.getBody()).contains("SAVE BOOK NAME");

        ResponseEntity<String> response2 = postMessage("other", Fixture.mmm.getSlackId());
        softly.assertThat(response2.getBody()).contains("CREATE REVIEW");

        Review review = reviewRepository.findByReview("other");
        softly.assertThat("other").isEqualTo(review.getReview());
    }

    @Test
    public void createReview() {
        ResponseEntity<String> response = postMessage("!리뷰 !등록", Fixture.mmm.getSlackId());
        softly.assertThat(response.getBody()).contains("ASK BOOK NAME");

        ResponseEntity<String> response1 = postMessage("첫 번째 책", Fixture.mmm.getSlackId());
        softly.assertThat(response1.getBody()).contains("ASK REVIEW CONTENTS");

        ResponseEntity<String> response2 = postMessage("다른 사람이 작성한 새로운 내용입니다.", Fixture.mmm.getSlackId());
        softly.assertThat(response2.getBody()).contains("CREATE REVIEW");

        Review review = reviewRepository.findByReview("다른 사람이 작성한 새로운 내용입니다.");
        softly.assertThat("다른 사람이 작성한 새로운 내용입니다.").isEqualTo(review.getReview());
    }

    @Test
    public void deleteReviewDirect() {
        Review review = new Review("원래 존재하는 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.mmm.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);
        int before = (int) reviewRepository.count();

        ResponseEntity<String> response = postMessage("첫 번째 책 !리뷰 !삭제", Fixture.mmm.getSlackId());
        softly.assertThat(response.getBody()).contains("DELETE REVIEW");

        int after = (int) reviewRepository.count();
        softly.assertThat(before - after).isEqualTo(1);
    }

    @Test
    public void deleteReviewManyBook() {
        Review review = new Review("원래 존재하는 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.mmm.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);
        int before = (int) reviewRepository.count();

        ResponseEntity<String> response = postMessage("번째 책 !리뷰 !삭제", Fixture.mmm.getSlackId());
        softly.assertThat(response.getBody()).contains("MANY BOOK");

        ResponseEntity<String> response1 = postMessage("1", Fixture.mmm.getSlackId());
        softly.assertThat(response1.getBody()).contains("DELETE REVIEW");

        int after = (int) reviewRepository.count();
        softly.assertThat(before - after).isEqualTo(1);
    }

    @Test
    public void deleteReview() {
        Review review = new Review("원래 존재하는 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.mmm.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);
        int before = (int) reviewRepository.count();
        ResponseEntity<String> response = postMessage("!리뷰 !삭제",Fixture.mmm.getSlackId());
        softly.assertThat(response.getBody()).contains("ASK BOOK NAME");

        ResponseEntity<String> response1 = postMessage("첫 번째 책",Fixture.mmm.getSlackId());
        softly.assertThat(response1.getBody()).contains("DELETE REVIEW");

        int after = (int) reviewRepository.count();
        softly.assertThat(before - after).isEqualTo(1);
    }

    @Test
    public void updateReviewDirect() {
        Review review = new Review("원래 존재하는 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.mmm.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);

        int before = (int) reviewRepository.count();

        ResponseEntity<String> response = postMessage("첫 번째 책 !리뷰 !수정",Fixture.mmm.getSlackId());
        softly.assertThat(response.getBody()).contains("ASK CONTENTS");

        ResponseEntity<String> response1 = postMessage("새로운 내용",Fixture.mmm.getSlackId());
        softly.assertThat(response1.getBody()).contains("CREATE REVIEW");

        int after = (int) reviewRepository.count();
        Review originReview = reviewRepository.findByReview("원래 존재하는 리뷰");
        Review updatedReview = reviewRepository.findByReview("새로운 내용");

        softly.assertThat(updatedReview).isNotNull();
        softly.assertThat(originReview).isNull();
        softly.assertThat(before - after).isEqualTo(0);

    }

    //    @Test
    public void updateReview() {
        ResponseEntity<String> response = postMessage("리뷰 수정");
        softly.assertThat(response.getBody()).contains("ASK BOOK NAME");

        ResponseEntity<String> response1 = postMessage("성공하는 프로그래밍 공부법");
        softly.assertThat(response1.getBody()).contains("ASK REVIEW CONTENTS");

        ResponseEntity<String> response2 = postMessage("새로운 내용입니다.");
        softly.assertThat(response2.getBody()).contains("DO REVIEW UPDATE OR CREATE");

        Book book = bookRepository.findByTitle("성공하는프로그래밍공부법").get();
        Review review = book.getReviewsBySlackId(Fixture.nnn.getSlackId());
        softly.assertThat(review.getReview()).isEqualTo("새로운 내용입니다.");
    }

    //    @Test
    public void showReviewDirect() {
        ResponseEntity<String> response = postMessage("성공하는 프로그래밍 공부법 리뷰");
        log.info("review : {}", response.toString());
        softly.assertThat(response.getBody()).contains("READ REVIEW");
    }

    //    @Test
    public void showReview() {
        ResponseEntity<String> response = postMessage("리뷰");
        log.info("requestReview : {}", response.toString());
        softly.assertThat(response.getBody()).contains("ASK BOOK NAME");

        ResponseEntity<String> response1 = postMessage("성공하는 프로그래밍 공부법");
        log.info("sendReview : {}", response1.toString());
        softly.assertThat(response1.getBody()).contains("READ REVIEW");
    }

    //    @Test
    public void matchBookName() {
        ResponseEntity<String> response = postMessage("성공하는 프로그래밍 공부법");
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("SEND BOOK INFO");
    }

    //    @Test
    public void echo() throws Exception {
        ResponseEntity<String> response = postMessage("에코 에코 에코");
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).isEqualTo("ECHO");
    }

    //    @Test
    public void eventSubscription() {
        String challengeToken = "3eZbrw1aBm2rZgRNFdxV2595E9CY3gmdALWMmHkvFXO7tYXAYM8P";
        String json = "{\"token\": \"Jhj5dZrVaK7ZwHHjRyZWjbDl\",\"challenge\": \"" + challengeToken + "\",\"type\": \"url_verification\"}";

        HttpEntity<String> request = makeRequest(json);

        ResponseEntity<String> response = template().postForEntity("/api/valid", request, String.class);
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).isEqualTo(challengeToken);
    }
}