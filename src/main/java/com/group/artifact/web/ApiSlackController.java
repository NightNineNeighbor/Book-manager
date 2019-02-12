package com.group.artifact.web;

import com.group.artifact.vo.MessageVo;
import com.group.artifact.state_collection.ChatBotState;
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
    private ChatBotState chatBotState;

    @PostMapping("/listener")
    public String listener(@RequestBody SlackAcceptor acceptor) {
        if (acceptor.isChallenge()) {
            return acceptor.getChallenge();
        }

        MessageVo messageVo = acceptor.parse();
        return chatBotState.doService(messageVo);
    }
}
