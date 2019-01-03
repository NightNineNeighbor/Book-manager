package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

public class ReadReviewWithoutBookName implements State{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        service.askBookName(serviceResolver.getChannel());
        return "ASK BOOK NAME";
    }

    @Override
    public State nextState(boolean isBookName, Command command){
        if (isBookName) {
            return new ReadReviewWithBookName();
        }
        return command.initState(isBookName);
    }
}