package com.group.artifact.state;

import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

public interface NeedBook extends State {
    void beforeService(SlackService service, ServiceResolver serviceResolver);

    State setBookName(String bookName);
}
