package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

public class StateZero implements State {
    static StateZero me = new StateZero();

    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        return serviceResolver.getCommand().initState().doService(service, serviceResolver);
    }
}
