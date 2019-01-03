package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

public class UpdateReviewWithBookName implements State{
    private String bookName;

    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        service.askReviewContents(serviceResolver.getChannel());
        this.bookName = serviceResolver.getText();
        return "ASK REVIEW CONTENTS";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        return new UpdateReviewExecute(bookName);
    }
}
