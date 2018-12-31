package com.group.artifact.state;

import com.group.artifact.service.SlackService;

public class CreateReviewWithBookName implements State{
    private String bookName;

    @Override
    public String doService(SlackService service, Data data) {
        service.askReviewContents(data.getChannel());
        this.bookName = data.getText();
        return "ASK REVIEW CONTENTS";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        return new CreateReviewExecute(bookName);
    }
}
