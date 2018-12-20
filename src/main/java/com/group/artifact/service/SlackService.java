package com.group.artifact.service;

import com.group.artifact.helper.RequestCreator;
import com.group.artifact.vo.SlackAcceptor;
import com.group.artifact.vo.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("slackService")
public class SlackService {
    @Autowired
    private Url url;

    @Autowired
    private RequestCreator requestCreator;

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> echo(SlackAcceptor acceptor) {
        return restTemplate.postForEntity(url.getPostMessage(),
                requestCreator.echo(acceptor),
                String.class);
    }
}
