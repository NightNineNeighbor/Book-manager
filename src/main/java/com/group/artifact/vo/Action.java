package com.group.artifact.web;

import com.group.artifact.service.SlackService;

public enum Action {
    Review_Read,
    Review_Delete,
    Review_Update,
    Review_Create,
    NoAction;

    public static Action parse(String text) {
        if (text.contains("리뷰")) {
            if (text.contains("등록")) {
                return Review_Create;
            } else if (text.contains("삭제")) {
                return Review_Delete;
            } else if (text.contains("수정")) {
                return Review_Update;
            }else{
                return Review_Read;
            }
        }
        return NoAction;
    }

    public void doAction(SlackService slackService) {

    }
}
