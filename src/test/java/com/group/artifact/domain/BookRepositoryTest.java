package com.group.artifact.domain;

import com.group.artifact.AcceptanceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookRepositoryTest extends AcceptanceTest {
    @Autowired
    private BookRepository bookRepository;
    @Test
    public void likeTest(){
        List<Book> books = bookRepository.findByTitleLike("%성공%");
        System.out.println(books);
    }


}
