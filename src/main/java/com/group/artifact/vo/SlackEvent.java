package com.group.artifact.vo;

import com.group.artifact.stateStarter.Command;
import com.group.artifact.service.ServiceResolver;

public class SlackEvent {
    private String type;
    private String subtype;
    private String text;
    private String user;
    private String channel;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public boolean isUserMessageEvent() {
        return this.type.equals("message") && subtype == null;
    }

    public ServiceResolver parse() {
        if (this.subtype != null && this.subtype.equals("bot_message")) {
            return new ServiceResolver(Command.DO_NOTHING);
        }

        Command command = Command.matchCommand(this.text);
        return new ServiceResolver(command,
                command.removeMatcher(this.text),
                this.user,
                this.channel);
    }
}
