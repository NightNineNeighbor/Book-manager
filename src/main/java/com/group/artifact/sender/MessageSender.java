package com.group.artifact.sender;

import com.group.artifact.domain.Book;
import com.group.artifact.domain.Review;
import com.group.artifact.helper.RequestCreator;
import com.group.artifact.vo.SlackAcceptor;
import com.group.artifact.vo.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    public ResponseEntity<String> review(List<Review> reviews, String channel) {
        return restTemplate.postForEntity(url.getPostMessage(),
                requestCreator.review(reviews, channel),
                String.class);
    }

//    public ResponseEntity<String> askBookName(String channel) {
//        return restTemplate.postForEntity(url.getPostMessage(),
//                requestCreator.simpleText("책 이름을 입력해 주세요", channel),
//                String.class);
//    }

    public ResponseEntity<String> send(String text, String channel) {
        return restTemplate.postForEntity(url.getPostMessage(),
                requestCreator.simpleText(text, channel),
                String.class);
    }
}
