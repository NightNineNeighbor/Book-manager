package com.group.artifact.sender;

import com.group.artifact.domain.Book;
import com.group.artifact.helper.RequestCreator;
import com.group.artifact.vo.SlackAcceptor;
import com.group.artifact.vo.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MessageSender {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Url url;

    @Autowired
    private RequestCreator requestCreator;

    public ResponseEntity<String> echo(SlackAcceptor acceptor) {
        return restTemplate.postForEntity(url.getPostMessage(),
                requestCreator.echo(acceptor),
                String.class);
    }

    public ResponseEntity<String> bookInfo(Book book, String channel) {
        return restTemplate.postForEntity(url.getPostMessage(),
                requestCreator.bookInfo(book, channel),
                String.class);
    }
}
