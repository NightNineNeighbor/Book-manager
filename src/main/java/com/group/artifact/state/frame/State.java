package com.group.artifact.state.frame;

import com.group.artifact.state.container.StateContainer;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public abstract class State {
    protected SlackService service;
    protected StateContainer stateContainer;

    public State(SlackService service, StateContainer stateContainer) {
        this.service = service;
        this.stateContainer = stateContainer;
    }

    public SlackService getService() {
        return service;
    }

    public StateContainer getStateContainer() {
        return stateContainer;
    }

    abstract public String doService(MessageVo messageVo);
}
