package com.group.artifact.domain;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.fixture.Fixture;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookRepositoryTest extends AcceptanceTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void likeTest() {
        bookRepository.save(Fixture.book);
        bookRepository.save(Fixture.book2);

        List<Book> books = bookRepository.findByTitleLike("%번째 책%");
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
