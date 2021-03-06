package com.group.artifact.vo;

import com.group.artifact.stateStarter.Command;

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

    public String getTextWithoutSpacer() {
        if (event != null) {
            return this.event.getText().replace(" ", "");
        }
        return "";
    }


    public MessageVo parse() {
        if (event == null) {
            return new MessageVo(Command.DO_NOTHING, "EVENT IS NULL", "", "");
        }
        return event.parse();
    }
}
