package com.group.artifact.service;

import com.group.artifact.state.ChatBotState;
import com.group.artifact.state.State;
import com.group.artifact.stateStarter.Command;

public class ServiceResolver {
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

    public ServiceResolver(Command command) {
        this.command = command;
    }

    public String doSomething(SlackService service) {
        State currentState = ChatBotState.currentState(slackId, command);
        return currentState.doService(service, this);
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
