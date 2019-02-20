package com.group.artifact.state.concrete;

import com.group.artifact.service.SlackService;
import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.State;
import com.group.artifact.vo.MessageVo;

public class MyReviews extends State {
    public MyReviews(SlackService service, StateContainer stateContainer) {
        super(service, stateContainer);
    }

    @Override
    public String doService(MessageVo messageVo) {
        service.allReview(messageVo.getSlackId(), messageVo.getChannel());
        return "MY REVIEWS";
    }
}
