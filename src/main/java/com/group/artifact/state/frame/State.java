package com.group.artifact.state.frame;

import com.group.artifact.state.container.StateContainer;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public interface State {
    String doService(SlackService service, MessageVo messageVo, StateContainer stateContainer);
}
