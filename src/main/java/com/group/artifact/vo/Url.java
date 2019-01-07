package com.group.artifact.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Url {
    @Value("${webhook}")
    private String webhook;
    @Value("${postMessage}")
    private String postMessage;
    @Value("${naverApi}")
    private String naverApi;

    public Url() {
    }

    public String getWebhook() {
        return webhook;
    }


    public String getPostMessage() {
        return postMessage;
    }

    public String getNaverApi() {
        return naverApi;
    }

    public String getNaverApi(String bookName) {
        return naverApi + "?display=1&sort=sim&query=" + bookName;
    }
}
