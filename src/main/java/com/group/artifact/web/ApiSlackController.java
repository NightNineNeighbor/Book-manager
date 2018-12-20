package com.group.artifact.web;

import com.group.artifact.service.SlackService;
import com.group.artifact.vo.SlackAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiSlackController {
    private static final Logger logger = LoggerFactory.getLogger(ApiSlackController.class);

    @Autowired
    SlackService slackService;

    //todo : change mapping url
    @PostMapping("/valid")
    public String valid(@RequestBody SlackAcceptor acceptor) throws Exception {
        if (acceptor.isUserMessageEvent()) {
            slackService.echo(acceptor);
            return "OK";
        }

        if (acceptor.isChallenge()) {
            return acceptor.getChallenge();
        }

        return "NOT_MATCH";
    }
}
