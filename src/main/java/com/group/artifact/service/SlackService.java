package com.group.artifact.service;

import com.group.artifact.domain.Book;
import com.group.artifact.domain.BookRepository;
import com.group.artifact.sender.MessageSender;
import com.group.artifact.vo.SlackAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("slackService")
public class SlackService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MessageSender messageSender;

    public ResponseEntity<String> echo(SlackAcceptor acceptor) {
        return messageSender.echo(acceptor);
    }

    public ResponseEntity<String> sendBookInfo(SlackAcceptor acceptor) {
        Book book = bookRepository.findByTitle(acceptor.getTextWithoutSpacer()).get();
        return messageSender.bookInfo(book, acceptor.getChannel());
    }

    public boolean isBookInfoRequest(SlackAcceptor acceptor) {
        return bookRepository.findByTitle(acceptor.getTextWithoutSpacer()).isPresent();
    }
}
