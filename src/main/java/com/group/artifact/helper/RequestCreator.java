package com.group.artifact.helper;

import com.group.artifact.domain.Book;
import com.group.artifact.format.BookInfo;
import com.group.artifact.vo.SlackAcceptor;
import com.group.artifact.vo.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RequestCreator {
    @Autowired
    private Token token;

    public HttpEntity<Map<String, String>> echo(SlackAcceptor acceptor) {
        HttpHeaders headers = getJsonHeaders();

        Map<String, String> map = new HashMap<>();
        map.put("channel", acceptor.getChannel());
        map.put("text", acceptor.getEchoText());

        return new HttpEntity<>(map, headers);
    }

    public HttpEntity<BookInfo> bookInfo(Book book, String channel) {
        HttpHeaders header = getJsonHeaders();

        BookInfo bookInfo = BookInfo.of(book, channel);
        return new HttpEntity<>(bookInfo, header);
    }

    private HttpHeaders getJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token.getBotToken());
        return headers;
    }
}
