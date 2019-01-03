package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

public class CreateReviewExecute implements State {
    private String bookName;

    public CreateReviewExecute(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        service.createReview(bookName, serviceResolver.getText(), serviceResolver.getSlackId());
        return "CREATE REVIEW";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        return command.initState(isBookName);
    }
}
