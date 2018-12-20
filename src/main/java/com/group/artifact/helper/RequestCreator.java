package com.group.artifact.helper;

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
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+ token.getBotToken() );

        Map<String, String> map = new HashMap<>();
        map.put("channel", acceptor.getChannel());
        map.put("text", acceptor.getEchoText());

        return new HttpEntity<>(map, headers);

    }
}
