package com.group.artifact.state;

import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.frame.State;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public class DoNothing implements State {
    @Override
    public String doService(SlackService service, MessageVo messageVo, StateContainer stateContainer) {
        return "DO NOTHING";
    }
}
