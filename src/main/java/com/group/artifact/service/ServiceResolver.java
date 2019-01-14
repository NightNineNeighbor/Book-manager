package com.group.artifact.service;

import com.group.artifact.domain.Book;
import com.group.artifact.state.ChatBotState;
import com.group.artifact.stateStarter.Command;
import com.group.artifact.state.State;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceResolver {
    private static Map<String, State> beforeState = new HashMap<>();

    private Command command;
    private String text;
    private String slackId;
    private String channel;

    public ServiceResolver(Command command, String text, String slackId, String channel) {
        this.command = command;
        this.text = text;
        this.slackId = slackId;
        this.channel = channel;
    }

    public String doSomething(SlackService service) {
        boolean isBookName = service.isBookName(text);
        List<Book> books = service.search(text);
        State currentState = ChatBotState.currentState(slackId, command, books);
        return currentState.doService(service, this);

//        if (beforeState.containsKey(slackId)) {
//            State currentState = beforeState.get(slackId).nextState(isBookName, command);
//            ret = currentState.doService(service, this);
//            beforeState.put(slackId, currentState);
//            return ret;
//        }
//        State currentState1 = State.of(isBookName, command);
//        ret = currentState1.doService(service, this);
//        beforeState.put(slackId, currentState1);
//        return ret;
    }

    public Command getCommand() {
        return command;
    }

    public String getText() {
        return text;
    }

    public String getSlackId() {
        return slackId;
    }

    public String getChannel() {
        return channel;
    }
}
