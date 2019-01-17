package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

public class ExitCommand implements State {
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        service.send("명령 상태 초기화", serviceResolver.getChannel());
        ChatBotState.remove(serviceResolver.getSlackId());
        return "EXIT STATE";
    }
}
