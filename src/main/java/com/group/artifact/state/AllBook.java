package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

public class AllBook implements State{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        service.allBook(serviceResolver.getChannel());
        return "ALL BOOK";
    }
}
