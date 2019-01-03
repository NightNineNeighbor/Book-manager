package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

public class ReadReviewWithBookName implements State{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        service.sendReview(serviceResolver.getText(), serviceResolver.getChannel());
        return "READ REVIEW";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        return command.initState(isBookName);
    }
}
