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

    TextAndAction parseMessage() {
        if (this.text.contains("리뷰")) {
            String bookName = this.text.replace("리뷰", "");
            if (bookName.contains("등록")) {
                bookName = bookName.replace("등록", "");
                TextAndAction.save(this.user, Action.ReviewCreate);
            }
            else if (bookName.contains("삭제")) {
                bookName = bookName.replace("삭제", "");
                TextAndAction.save(this.user, Action.ReviewDelete);
            } else if (bookName.contains("수정")) {
                bookName = bookName.replace("수정", "");
                TextAndAction.save(this.user, Action.ReviewUpdate);
            } else {
                TextAndAction.save(this.user, Action.ReviewRead);
            }
            return new TextAndAction(this.user, bookName);
        }
        return new TextAndAction(this.user, this.text);
    }

    public ServiceResolver parse() {
        if (this.text.contains("리뷰")) {
            String pureText = this.text.replace("리뷰", "");
            Command command;
            if (this.text.contains("등록")) {
                pureText = pureText.replace("등록", "");
                command = Command.REVIEW_CREATE;
            }else if(this.text.contains("삭제")){
                pureText = pureText.replace("삭제", "");
                command = Command.REVIEW_DELETE;
            }else if (this.text.contains("수정")){
                pureText = pureText.replace("수정", "");
                command = Command.REVIEW_UPDATE;
            }else{
                command = Command.REVIEW_READ;
            }
            return new ServiceResolver(command, pureText, this.user, this.channel);
        }
        return new ServiceResolver(Command.NO_COMMAND, this.text, this.user, this.channel);
    }
}
