package com.group.artifact.vo;

import com.group.artifact.service.SlackService;
import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.State;
import com.group.artifact.stateStarter.Command;

public class MessageVo {
    private Command command;
    private String text;
    private String slackId;
    private String channel;

    public MessageVo(Command command, String text, String slackId, String channel) {
        this.command = command;
        this.text = text;
        this.slackId = slackId;
        this.channel = channel;
    }

    public MessageVo(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public String getText() {
        return text;
    }

    public String getChannel() {
        return channel;
    }

    public State initState(SlackService slackService, StateContainer stateContainer) {
        return this.command.initState(slackService, stateContainer);
    }

    public boolean isInterrupt() {
        return this.command == Command.EXIT_COMMAND_MODE || command == Command.USAGE;
    }

    public String getSlackId() {
        return this.slackId;
    }
}
