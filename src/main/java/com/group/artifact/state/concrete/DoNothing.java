package com.group.artifact.state.concrete;

import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.State;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public class DoNothing extends State {

    public DoNothing(SlackService service, StateContainer stateContainer) {
        super(service, stateContainer);
    }

    @Override
    public String doService(MessageVo messageVo) {
        return "DO NOTHING";
    }
}
