package com.group.artifact.state;

import com.group.artifact.service.SlackService;
import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.frame.State;
import com.group.artifact.vo.MessageVo;

public class ExitCommand extends State {
    public ExitCommand(SlackService service, StateContainer stateContainer) {
        super(service, stateContainer);
    }

    @Override
    public String doService(MessageVo messageVo) {
        service.send("명령 상태 초기화", messageVo.getChannel());
        stateContainer.remove(messageVo);
        return "EXIT STATE";
    }
}
