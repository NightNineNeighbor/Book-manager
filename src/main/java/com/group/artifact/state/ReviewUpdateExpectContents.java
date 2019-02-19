package com.group.artifact.state;

import com.group.artifact.service.SlackService;
import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.frame.NeedBookNameState;
import com.group.artifact.stateStarter.Command;
import com.group.artifact.vo.MessageVo;


public class ReviewUpdateExpectContents extends NeedBookNameState {
    private String bookName;

    public ReviewUpdateExpectContents(SlackService service, StateContainer stateContainer, String bookName) {
        super(service, stateContainer);
        this.bookName = bookName;
    }

    public ReviewUpdateExpectContents(SlackService service, StateContainer stateContainer) {
        super(service, stateContainer);
    }

    @Override
    public String doService(MessageVo messageVo) {
        if (messageVo.getCommand() == Command.NO_COMMAND) {
            service.updateOrCreateReview(bookName, messageVo.getText(), messageVo.getSlackId(), messageVo.getChannel());
            stateContainer.remove(messageVo);
            return "CREATE REVIEW";
        }
        return messageVo.getCommand().initState(service, stateContainer).doService(messageVo);
    }

    @Override
    public String serviceWithBookName(MessageVo messageVo, String bookName) {
        this.bookName = bookName;
        service.askReviewContents(messageVo.getChannel());
        stateContainer.put(messageVo, this);
        return "SAVE BOOK NAME";
    }

}
