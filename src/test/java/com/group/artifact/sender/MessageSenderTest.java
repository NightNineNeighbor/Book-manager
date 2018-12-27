package com.group.artifact.sender;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.domain.Book;
import com.group.artifact.fixture.Fixture;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class MessageSenderTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(MessageSenderTest.class);

    @Autowired
    MessageSender sender;

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