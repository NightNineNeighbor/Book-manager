package com.group.artifact.state.container;

import com.group.artifact.service.SlackService;
import com.group.artifact.state.State;
import com.group.artifact.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StateContainer {
    @Autowired
    private SlackService service;
    private Map<String, State> savedState = new HashMap<>();

    public String doService(MessageVo messageVo) {
        State currentState = currentState(messageVo);
        return currentState.doService(messageVo);
    }

    public void put(MessageVo messageVo, State state) {
        savedState.put(messageVo.getSlackId(), state);
    }

    public void remove(MessageVo messageVo) {
        savedState.remove(messageVo.getSlackId());
    }

    private State currentState(MessageVo messageVo) {
        if (messageVo.isInterrupt()) {
            return messageVo.initState(service, this);
        }

        if (savedState.containsKey(messageVo.getSlackId())) {
            return savedState.get(messageVo.getSlackId());
        }
        return messageVo.initState(service, this);
    }
}
