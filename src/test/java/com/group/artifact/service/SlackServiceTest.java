package com.group.artifact.service;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.vo.SlackAcceptor;
import com.group.artifact.vo.SlackEvent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class SlackServiceTest extends AcceptanceTest {

    @Autowired
    SlackService slackService;

    @Test
    public void echo() {
        SlackEvent event = new SlackEvent();
        event.setChannel("CD7KGTJ3E");
        event.setUser("UD7QN97QS");
        event.setText("test text");

        SlackAcceptor acceptor = new SlackAcceptor();
        acceptor.setEvent(event);

        ResponseEntity<String> response = slackService.echo(acceptor);
        System.out.println(response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }
}