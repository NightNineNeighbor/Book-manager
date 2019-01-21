package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

public class Usage implements State {
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        service.usage(serviceResolver.getChannel());
        return "PRINT USAGE";
    }
}