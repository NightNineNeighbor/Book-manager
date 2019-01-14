package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;


public class ReviewCreateExpectContents implements State, NeedBook {
    private String bookName;

    ReviewCreateExpectContents(String bookName) {
        this.bookName = bookName;
    }

    ReviewCreateExpectContents() {}

    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        if (serviceResolver.getCommand() == Command.NO_COMMAND) {
            service.createReview(bookName, serviceResolver.getChannel(), serviceResolver.getSlackId());
            ChatBotState.put(serviceResolver.getSlackId(), StateZero.me);
            return "CREATE REVIEW";
        }
        return serviceResolver.getCommand().initState().doService(service, serviceResolver);
    }

    @Override
    public void beforeService(SlackService service, ServiceResolver serviceResolver) {
        service.askReviewContents(serviceResolver.getChannel());
    }

    @Override
    public State setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }
}
