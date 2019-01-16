package com.group.artifact.web;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.domain.BookRepository;
import com.group.artifact.domain.Review;
import com.group.artifact.domain.ReviewRepository;
import com.group.artifact.domain.UserRepository;
import com.group.artifact.fixture.Fixture;
import com.group.artifact.helper.DBInitializer;
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

    @Autowired
    private DBInitializer dbInitializer;

    @Before
    public void before() {
        dbInitializer.inite();
    }

    @Test
    public void createReviewManyBook() {
        ResponseEntity<String> response = postMessage("번째 책 !리뷰 !등록", Fixture.otherUser.getSlackId());
        softly.assertThat(response.getBody()).contains("MANY BOOK");

        ResponseEntity<String> response1 = postMessage("1", Fixture.otherUser.getSlackId());
        softly.assertThat(response1.getBody()).contains("SAVE BOOK NAME");

        ResponseEntity<String> response2 = postMessage("other", Fixture.otherUser.getSlackId());
        softly.assertThat(response2.getBody()).contains("CREATE REVIEW");

        Review review = reviewRepository.findByReview("other");
        softly.assertThat("other").isEqualTo(review.getReview());
    }

    @Test
    public void createReview() {
        ResponseEntity<String> response = postMessage("!리뷰 !등록", Fixture.otherUser.getSlackId());
        softly.assertThat(response.getBody()).contains("ASK BOOK NAME");

        ResponseEntity<String> response1 = postMessage("첫 번째 책", Fixture.otherUser.getSlackId());
        softly.assertThat(response1.getBody()).contains("ASK REVIEW CONTENTS");

        ResponseEntity<String> response2 = postMessage("다른 사람이 작성한 새로운 내용입니다.", Fixture.otherUser.getSlackId());
        softly.assertThat(response2.getBody()).contains("CREATE REVIEW");

        Review review = reviewRepository.findByReview("다른 사람이 작성한 새로운 내용입니다.");
        softly.assertThat("다른 사람이 작성한 새로운 내용입니다.").isEqualTo(review.getReview());
    }

    @Test
    public void deleteReviewDirect() {
        userRepository.save(Fixture.otherUser);
        Review review = new Review("원래 존재하는 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.otherUser.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);
        int before = (int) reviewRepository.count();

        ResponseEntity<String> response = postMessage("첫 번째 책 !리뷰 !삭제", Fixture.otherUser.getSlackId());
        softly.assertThat(response.getBody()).contains("DELETE REVIEW");

        int after = (int) reviewRepository.count();
        softly.assertThat(before - after).isEqualTo(1);
    }

    @Test
    public void deleteReviewManyBook() {
        userRepository.save(Fixture.otherUser);
        Review review = new Review("원래 존재하는 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.otherUser.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);
        int before = (int) reviewRepository.count();

        ResponseEntity<String> response = postMessage("번째 책 !리뷰 !삭제", Fixture.otherUser.getSlackId());
        softly.assertThat(response.getBody()).contains("MANY BOOK");

        ResponseEntity<String> response1 = postMessage("1", Fixture.otherUser.getSlackId());
        softly.assertThat(response1.getBody()).contains("DELETE REVIEW");

        int after = (int) reviewRepository.count();
        softly.assertThat(before - after).isEqualTo(1);
    }

    @Test
    public void deleteReview() {
        userRepository.save(Fixture.otherUser);
        Review review = new Review("원래 존재하는 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.otherUser.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);
        int before = (int) reviewRepository.count();
        ResponseEntity<String> response = postMessage("!리뷰 !삭제", Fixture.otherUser.getSlackId());
        softly.assertThat(response.getBody()).contains("ASK BOOK NAME");

        ResponseEntity<String> response1 = postMessage("첫 번째 책", Fixture.otherUser.getSlackId());
        softly.assertThat(response1.getBody()).contains("DELETE REVIEW");

        int after = (int) reviewRepository.count();
        softly.assertThat(before - after).isEqualTo(1);
    }

    @Test
    public void updateReviewDirect() {
        userRepository.save(Fixture.otherUser);
        Review review = new Review("원래 존재하는 리뷰", null, null);
        review.setWriter(userRepository.findBySlackId(Fixture.otherUser.getSlackId()).get());
        review.setBook(bookRepository.findByTitle(Fixture.book.getTitle()).get());
        reviewRepository.save(review);

        int before = (int) reviewRepository.count();

        ResponseEntity<String> response = postMessage("첫 번째 책 !리뷰 !수정", Fixture.otherUser.getSlackId());
        softly.assertThat(response.getBody()).contains("ASK CONTENTS");

        ResponseEntity<String> response1 = postMessage("새로운 내용", Fixture.otherUser.getSlackId());
        softly.assertThat(response1.getBody()).contains("CREATE REVIEW");

        int after = (int) reviewRepository.count();
        Review originReview = reviewRepository.findByReview("원래 존재하는 리뷰");
        Review updatedReview = reviewRepository.findByReview("새로운 내용");

        softly.assertThat(updatedReview).isNotNull();
        softly.assertThat(originReview).isNull();
        softly.assertThat(before - after).isEqualTo(0);
    }


    @Test
    public void readReviewDirect() {
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

        ResponseEntity<String> response = postMessage("첫 번째 책 !리뷰", Fixture.otherUser.getSlackId());
        softly.assertThat(response.getBody()).contains("READ REVIEW");
    }

    @Test
    public void readReviewManyBooks() {
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

        ResponseEntity<String> response = postMessage("번째 책 !리뷰", Fixture.otherUser.getSlackId());
        softly.assertThat(response.getBody()).contains("MANY BOOK");

        ResponseEntity<String> response1 = postMessage("1", Fixture.otherUser.getSlackId());
        softly.assertThat(response1.getBody()).contains("READ REVIEW");
    }

    @Test
    public void reviewBook() {
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

        ResponseEntity<String> response = postMessage("!리뷰", Fixture.otherUser.getSlackId());
        softly.assertThat(response.getBody()).contains("ASK BOOK NAME");

        ResponseEntity<String> response1 = postMessage("첫 번째 책", Fixture.otherUser.getSlackId());
        softly.assertThat(response1.getBody()).contains("READ REVIEW");
    }

    @Test
    public void bookInfoDirect() {
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


        ResponseEntity<String> response = postMessage("첫 번째 책 !정보");
        softly.assertThat(response.getBody()).contains("BOOK INFO");
    }


    @Test
    public void eventSubscription() {
        String challengeToken = "3eZbrw1aBm2rZgRNFdxV2595E9CY3gmdALWMmHkvFXO7tYXAYM8P";
        String json = "{\"token\": \"Jhj5dZrVaK7ZwHHjRyZWjbDl\",\"challenge\": \"" + challengeToken + "\",\"type\": \"url_verification\"}";

        HttpEntity<String> request = makeRequest(json);

        ResponseEntity<String> response = template().postForEntity("/api/valid", request, String.class);
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).isEqualTo(challengeToken);
    }
}