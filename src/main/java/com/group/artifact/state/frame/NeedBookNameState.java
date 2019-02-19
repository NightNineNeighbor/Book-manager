package com.group.artifact.state.frame;

import com.group.artifact.state_collection.ChatBotState;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public interface NeedBookNameState extends State {
    String serviceWithBookName(SlackService service, MessageVo messageVo, String bookName, ChatBotState chatBotState);

}
