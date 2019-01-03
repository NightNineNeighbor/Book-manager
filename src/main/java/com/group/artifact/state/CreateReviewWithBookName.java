package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

public class CreateReviewWithBookName implements State{
    String bookName;
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        this.bookName = serviceResolver.getText();
        service.askReviewContents(serviceResolver.getChannel());
        return "ASK REVIEW CONTENTS";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        return new CreateReviewExecute(bookName);
    }
}
