package com.group.artifact.state;

import com.group.artifact.service.SlackService;

public class CreateReviewExecute implements State{
    private String bookName;

    public CreateReviewExecute(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String doService(SlackService service, Data data) {
        service.save(bookName, data.getText(), data.getSlackId());
        return "DO REVIEW UPDATE OR CREATE";
    }

    @Override
    public State nextState(boolean isBookName, Command command) {
        return command.nextState(isBookName);
    }
}
