package com.group.artifact.state.state_interface;

import com.group.artifact.state_collection.ChatBotState;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public interface State {
    String doService(SlackService service, MessageVo messageVo, ChatBotState chatBotState);
}
