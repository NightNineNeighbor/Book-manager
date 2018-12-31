package com.group.artifact.state;

import com.group.artifact.service.SlackService;

public class CreateReviewWithoutBookName implements State {
    @Override
    public String doService(SlackService service, Data data) {
        service.askBookName(data.getChannel());
        return "ASK BOOK NAME";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        if (isBookName) {
            return new CreateReviewWithBookName();
        }
        return command.nextState(isBookName);
    }

}
