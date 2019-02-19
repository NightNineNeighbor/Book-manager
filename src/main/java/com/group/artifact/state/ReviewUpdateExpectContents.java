package com.group.artifact.state;

import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;
import com.group.artifact.state_collection.ChatBotState;
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
    public String doService(SlackService service, MessageVo messageVo, ChatBotState chatBotState) {
        if (messageVo.getCommand() == Command.NO_COMMAND) {
            service.updateOrCreateReview(bookName, messageVo.getText(), messageVo.getSlackId(), messageVo.getChannel());
            chatBotState.remove(messageVo);
            return "CREATE REVIEW";
        }
        return messageVo.getCommand().initState().doService(service, messageVo, chatBotState);
    }

    @Override
    public String serviceWithBookName(SlackService service, MessageVo messageVo, String bookName, ChatBotState chatBotState) {
        this.bookName = bookName;
        service.askReviewContents(messageVo.getChannel());
        chatBotState.put(messageVo, this);
        return "SAVE BOOK NAME";
    }

}
