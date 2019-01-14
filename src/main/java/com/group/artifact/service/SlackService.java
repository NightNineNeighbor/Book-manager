package com.group.artifact.service;

import com.group.artifact.domain.*;
import com.group.artifact.sender.MessageSender;
import com.group.artifact.vo.SlackAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<String> sendReview(String bookName, String channel) {
        Book book = bookRepository.findByTitle(bookName.replace(" ", "")).get();
        return messageSender.review(book.getReviews(), channel);
    }

    public ResponseEntity<String> askBookName(String channel) {
        return messageSender.send("책 이름을 입력해 주세요", channel);
    }

    public ResponseEntity<String> askReviewContents(String channel) {
        return messageSender.send("리뷰 내용을 입력해 주세요", channel);
    }

    public Review updateReview(String bookName, String text, String slackId) {
        Book book = bookRepository.findByTitle(bookName.replace(" ", "")).orElseThrow(RuntimeException::new);//todo
        User user = userRepository.findBySlackId(slackId).orElseThrow(RuntimeException::new);//todo
        book.update(user,text);
        Review review = book.getReviewsBySlackId(slackId);
        return reviewRepository.save(review);
    }

    private User createUser(String slackId) {
        return userRepository.save(new User(0, slackId, new ArrayList<>()));
    }

    @Transactional
    public ResponseEntity<String> delete(String bookName, String user, String channel) {
        Book book = bookRepository.findByTitle(bookName.replace(" ", "")).get();
        Review review = book.getReviewsBySlackId(user);
        reviewRepository.delete(review);
        return messageSender.send(bookName + "의 <@" + user + "> 가 쓴 리뷰가 삭제되었습니다.", channel);
    }

    public ResponseEntity<String> echo(String slackId, String text, String channel) {
        return messageSender.send("<@" + slackId + ">'s echo : " + text, channel);
    }

    public Review createReview(String bookName, String text, String slackId) {
        Book book = bookRepository.findByTitle(bookName.replace(" ", "")).orElseThrow(RuntimeException::new);   //todo
        Optional<User> maybeUser = userRepository.findBySlackId(slackId);
        if (maybeUser.isPresent()) {
            return reviewRepository.save(new Review(0, text, book, maybeUser.get()));
        }
        User user = createUser(slackId);
        return reviewRepository.save(new Review(0, text, book, user));
    }

    public List<Book> search(String query) {
        List<String> queries = Arrays.asList(query.split(" "));
        if (queries.size() == 0) {
            throw new RuntimeException("Empty query");    //todo
        }
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
}
