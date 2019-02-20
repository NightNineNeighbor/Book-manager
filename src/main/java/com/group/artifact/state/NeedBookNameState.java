package com.group.artifact.state;

import com.group.artifact.state.container.StateContainer;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public abstract class NeedBookNameState extends State {

    public NeedBookNameState(SlackService service, StateContainer stateContainer) {
        super(service, stateContainer);
    }

    abstract public String serviceWithBookName(MessageVo messageVo, String bookName);

}
