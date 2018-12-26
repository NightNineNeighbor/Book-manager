package com.group.artifact.vo;

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

    public boolean isBookInfoRequest() {
        return this.text.contains("성공하는 프로그래밍 공부법");    //todo : make match algorithm
    }
}
