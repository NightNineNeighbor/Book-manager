package com.group.artifact.helper;

import com.group.artifact.domain.BookRepository;
import com.group.artifact.domain.ReviewRepository;
import com.group.artifact.domain.UserRepository;
import com.group.artifact.fixture.Fixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBInitializer {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public void inite() {
        bookRepository.deleteByTitle(Fixture.book.getTitle());
        bookRepository.deleteByTitle(Fixture.book2.getTitle());
        userRepository.deleteBySlackId(Fixture.otherUser.getSlackId());
        userRepository.deleteBySlackId(Fixture.defaultUser.getSlackId());
        bookRepository.save(Fixture.book);
        bookRepository.save(Fixture.book2);
    }

}
