package com.group.artifact.web;

import com.group.artifact.service.SlackService;
import com.group.artifact.service.ServiceResolver;
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
    private SlackService slackService;

    @PostMapping("/listener")
    public String listener(@RequestBody SlackAcceptor acceptor) {
        if (acceptor.isChallenge()) {
            return acceptor.getChallenge();
        }

        ServiceResolver serviceResolver = acceptor.parse();
        return serviceResolver.doSomething(slackService);
    }
}
