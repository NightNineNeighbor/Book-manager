package com.group.artifact.state;

import com.group.artifact.state_collection.ChatBotState;
import com.group.artifact.state.frame.State;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public class Usage implements State {
    @Override
    public String doService(SlackService service, MessageVo messageVo, ChatBotState chatBotState) {
        service.usage(messageVo.getChannel());
        return "PRINT USAGE";
    }
}
