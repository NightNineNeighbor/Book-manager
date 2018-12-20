package com.group.artifact.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Url {
    @Value("${webhook}")
    private String webhook;
    @Value("${postMessage}")
    private String postMessage;

    public Url() {
    }

    public String getWebhook() {
        return webhook;
    }


    public String getPostMessage() {
        return postMessage;
    }
}
