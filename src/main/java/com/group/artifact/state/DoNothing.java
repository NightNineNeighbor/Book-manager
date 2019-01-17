package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

public class DoNothing implements State {
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        return "DO NOTHING";
    }
}
