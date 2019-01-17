package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;


public class ReviewUpdateExpectContents implements State, NeedBookName {
    private String bookName;

    ReviewUpdateExpectContents(String bookName) {
        this.bookName = bookName;
    }

    ReviewUpdateExpectContents() {}

    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        if (serviceResolver.getCommand() == Command.NO_COMMAND) {
            service.updateOrCreateReview(bookName, serviceResolver.getText(), serviceResolver.getSlackId(),serviceResolver.getChannel());
            ChatBotState.remove(serviceResolver.getSlackId());
            return "CREATE REVIEW";
        }
        return serviceResolver.getCommand().initState().doService(service, serviceResolver);
    }

    @Override
    public String serviceWithBookName(SlackService service, ServiceResolver serviceResolver, String bookName) {
        this.bookName = bookName;
        service.askReviewContents(serviceResolver.getChannel());
        ChatBotState.put(serviceResolver.getSlackId(), this);
        return "SAVE BOOK NAME";
    }

}
