package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

public interface State {
    String doService(SlackService service, ServiceResolver serviceResolver);
}
