package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

public class DeleteReviewWithBookName implements State{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        service.delete(serviceResolver.getText(), serviceResolver.getSlackId(), serviceResolver.getChannel());
        return "DELETE REVIEW";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        return command.initState(isBookName);
    }
}
