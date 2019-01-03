package com.group.artifact.state;

import com.group.artifact.domain.Review;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

public class UpdateReviewExecute implements State{
    private String bookName;

    public UpdateReviewExecute(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        Review review = service.updateReview(bookName, serviceResolver.getText(), serviceResolver.getSlackId());
        return "DO REVIEW UPDATE OR CREATE";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        return command.initState(isBookName);
    }
}
