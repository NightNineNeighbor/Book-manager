package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

public class MyReviews implements State{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        service.allReview(serviceResolver.getSlackId(), serviceResolver.getChannel());
        return "MY REVIEWS";
    }
}
