package com.group.artifact.state;

import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;
import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.frame.NeedBookNameState;
import com.group.artifact.vo.MessageVo;


public class ReviewUpdateExpectContents implements NeedBookNameState {
    private String bookName;

    ReviewUpdateExpectContents(String bookName) {
        this.bookName = bookName;
    }

    ReviewUpdateExpectContents() {
    }

    @Override
    public String doService(SlackService service, MessageVo messageVo, StateContainer stateContainer) {
        if (messageVo.getCommand() == Command.NO_COMMAND) {
            service.updateOrCreateReview(bookName, messageVo.getText(), messageVo.getSlackId(), messageVo.getChannel());
            stateContainer.remove(messageVo);
            return "CREATE REVIEW";
        }
        return messageVo.getCommand().initState().doService(service, messageVo, stateContainer);
    }

    @Override
    public String serviceWithBookName(SlackService service, MessageVo messageVo, String bookName, StateContainer stateContainer) {
        this.bookName = bookName;
        service.askReviewContents(messageVo.getChannel());
        stateContainer.put(messageVo, this);
        return "SAVE BOOK NAME";
    }

}
