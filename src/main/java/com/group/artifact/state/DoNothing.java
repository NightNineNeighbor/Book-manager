package com.group.artifact.state;

import com.group.artifact.state_collection.ChatBotState;
import com.group.artifact.state.state_interface.State;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public class DoNothing implements State {
    @Override
    public String doService(SlackService service, MessageVo messageVo, ChatBotState chatBotState) {
        return "DO NOTHING";
    }
}
