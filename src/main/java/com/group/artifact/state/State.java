package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

public interface State {
    static State of(boolean isBookName, Command command) {
        return command.initState(isBookName);
    }

    String doService(SlackService service, ServiceResolver serviceResolver);

    State nextState(boolean isBookName, Command command);


}
