package com.group.artifact.state_interface;

import com.group.artifact.state_collection.ChatBotState;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public interface NeedBookName extends State {
    String serviceWithBookName(SlackService service, MessageVo messageVo, String bookName, ChatBotState chatBotState);

}
