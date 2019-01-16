package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

public interface NeedBookName extends State {
    String serviceWithBookName(SlackService service, ServiceResolver serviceResolver, String bookName);

}
