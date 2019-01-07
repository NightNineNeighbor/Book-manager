package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

public class DoNothing implements State{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        return "DO NOTHING";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        return command.initState(isBookName);
    }
}
