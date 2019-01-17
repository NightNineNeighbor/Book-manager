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
            return new ServiceResolver(Command.NO_COMMAND);
        }

        if (this.text.contains("!모든책")) {
            return new ServiceResolver(Command.ALL_BOOK);
        }

        if (this.text.contains("!나의리뷰")) {
            return new ServiceResolver(Command.MY_REVIEWS);
        }

        if (this.text.contains("!탈출")) {
            return new ServiceResolver(Command.EXIT_COMMAND_MODE);
        }

        if (this.text.contains("!사용법")) {
            return new ServiceResolver(Command.USAGE);
        }

        if (this.text.contains("!정보")) {
            return new ServiceResolver(Command.BOOK_INFO,
                    this.text.replace("!정보", ""),
                    this.user,
                    this.channel);
        }

        if (this.text.contains("!리뷰")) {
            String pureText = this.text.replace("!리뷰", "");
            Command command;
            if (this.text.contains("!등록")) {
                pureText = pureText.replace("!등록", "");
                command = Command.REVIEW_CREATE;
            }else if(this.text.contains("!삭제")){
                pureText = pureText.replace("!삭제", "");
                command = Command.REVIEW_DELETE;
            }else if (this.text.contains("!수정")){
                pureText = pureText.replace("!수정", "");
                command = Command.REVIEW_UPDATE;
            }else{
                command = Command.REVIEW_READ;
            }
            return new ServiceResolver(command, pureText, this.user, this.channel);
        }
        return new ServiceResolver(Command.NO_COMMAND, this.text, this.user, this.channel);
    }
}
