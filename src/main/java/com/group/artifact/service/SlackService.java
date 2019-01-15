package com.group.artifact.service;

import com.group.artifact.domain.*;
import com.group.artifact.sender.MessageSender;
import com.group.artifact.vo.SlackAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("slackService")
public class SlackService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MessageSender messageSender;

    public ResponseEntity<String> echo(SlackAcceptor acceptor) {
        return messageSender.echo(acceptor);
    }

    public ResponseEntity<String> sendBookInfo(String bookName, String channel) {
        Book book = bookRepository.findByTitle(bookName.replace(" ", "")).get();
        return messageSender.bookInfo(book, channel);
    }

    public boolean isBookName(SlackAcceptor acceptor) {
        return bookRepository.findByTitle(acceptor.getTextWithoutSpacer()).isPresent();
    }

    public boolean isBookName(String bookName) {
        return bookRepository.findByTitle(bookName.replace(" ","" )).isPresent();
    }

    public ResponseEntity<String> askBookName(String channel) {
        return messageSender.send("책 이름을 입력해 주세요", channel);
    }

    public ResponseEntity<String> askReviewContents(String channel) {
        return messageSender.send("리뷰 내용을 입력해 주세요", channel);
    }

    public Review deleteReview(String bookName, String slackId, String channel) {
        Book book = bookRepository.findByTitle(bookName).orElseThrow(()->new RuntimeException("틀린 책 이름"));
        User user = userRepository.findBySlackId(slackId).orElseThrow(() -> new RuntimeException("틀린 슬랙 ID"));
        Review review = reviewRepository.findByBook_IdAndWriter_Id(book.getId(), user.getId()).orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다"));
        reviewRepository.deleteById(review.getId());

        messageSender.send("아래 리뷰가 삭제되었습니다.", channel);
        messageSender.sendReview(channel, review);

        return review;
    }

    public ResponseEntity<String> echo(String slackId, String text, String channel) {
        return messageSender.send("<@" + slackId + ">'s echo : " + text, channel);
    }

    public Review updateOrCreateReview(String bookName, String text, String slackId, String channel) {
        Book book = bookRepository.findByTitle(bookName).orElseThrow(RuntimeException::new);
        Optional<User> maybeUser = userRepository.findBySlackId(slackId);
        if (!maybeUser.isPresent()) {
            maybeUser = Optional.of(createUser(slackId));
        }
        Optional<Review> maybeReview = reviewRepository.findByBook_IdAndWriter_Id(book.getId(), maybeUser.get().getId());
        Review review;
        if (maybeReview.isPresent()) {
            maybeReview.get().setReview(text);
            review = maybeReview.get();
        }else{
            review = new Review(text, book, maybeUser.get());
        }
        messageSender.sendReview(channel, review);
        return reviewRepository.save(review);
    }


    public User createUser(String slackId) {
        System.out.println("CREATE USER!!!!" + slackId);
        return userRepository.save(new User(slackId, new ArrayList<>()));
    }

    public List<Book> search(String query) {
        List<String> queries = Arrays.asList(query.split(" "));
        if (queries.size() == 0) {
            return new ArrayList<>();
        }
        List<Book> debug = bookRepository.findAll(); //debug
        List<Book> books = bookRepository.findByTitleLike("%"+queries.get(0)+"%");
        for (int i = 1; i < queries.size(); i++) {
            if (books.size() == 1) {
                return books;
            }else if (books.size() == 0) {
                books = bookRepository.findByTitleLike("%"+queries.get(i)+"%");
            }else {
                books = search(books, queries.get(i));
            }
        }
        return books;
    }

    private List<Book> search(List<Book> books, String s) {
        List<Book> ret = new ArrayList<>();
        for (Book book : books) {
            if (book.containInTitle(s)) {
                ret.add(book);
            }
        }
        if (ret.size() == 0) {
            return books;
        }
        return ret;
    }


    public ResponseEntity<String>  selectBook(String channel, List<Book> books) {
       return messageSender.sendBooks(channel, books);
    }

    public void error(String s) { //todo

    }

    @Transactional
    public ResponseEntity<String> readReview(String bookName, String channel) {
        Book book = bookRepository.findByTitle(bookName).orElseThrow(() -> new RuntimeException("잘못된 책 이름입니다."));
        List<Review> reviews = book.getReviews();
        return messageSender.review(reviews, channel);
    }
}
