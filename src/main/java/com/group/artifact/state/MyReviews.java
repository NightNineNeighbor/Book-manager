package com.group.artifact.state;

import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.frame.State;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

public class MyReviews implements State {
    @Override
    public String doService(SlackService service, MessageVo messageVo, StateContainer stateContainer) {
        service.allReview(messageVo.getSlackId(), messageVo.getChannel());
        return "MY REVIEWS";
    }
}
