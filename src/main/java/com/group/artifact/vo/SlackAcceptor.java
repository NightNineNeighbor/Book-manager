package com.group.artifact.vo;

public class SlackAcceptor {
    SlackEvent event;
    private String challenge;

    public SlackEvent getEvent() {
        return event;
    }

    public void setEvent(SlackEvent event) {
        this.event = event;
    }

    public String getChallenge() {
        return challenge;
    }

    public boolean isChallenge() {
        return this.challenge != null;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public boolean isUserMessageEvent() {
        return event != null && event.isUserMessageEvent();
    }

    public String getChannel() {
        return event.getChannel();
    }

    public String getEchoText() {
        return "<@" + event.getUser() + ">'s echo ~~~~" + event.getText();
    }

    public boolean isBookInfoRequest() {
        return event.isBookInfoRequest();
    }

    public String getTextWithoutSpacer() {
        if (event != null) {
            return this.event.getText().replace(" ", "");
        }
        return "";
    }
}
