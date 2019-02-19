package com.group.artifact.state;

import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.frame.State;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public class ExitCommand implements State {
    @Override
    public String doService(SlackService service, MessageVo messageVo, StateContainer stateContainer) {
        service.send("명령 상태 초기화", messageVo.getChannel());
        stateContainer.remove(messageVo);
        return "EXIT STATE";
    }
}
