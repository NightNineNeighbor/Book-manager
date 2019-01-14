package com.group.artifact.service;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.domain.Book;
import com.group.artifact.fixture.Fixture;
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

    @Test
    public void search(){
        List<Book> books = slackService.search("성공하는 프로그래밍 공부법");
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Test
    public void searchTwoResult() {
        List<Book> books = slackService.search("성공");
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