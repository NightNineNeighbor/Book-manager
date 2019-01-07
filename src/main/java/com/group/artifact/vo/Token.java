package com.group.artifact.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class Token {
    private String botToken;
    private String naverClientId;
    private String naverClientSecret;

    @Autowired
    private ResourceLoader resourceLoader;

    public String getBotToken() {
        if (this.botToken == null) {
            this.botToken = readToken("BotToken");
        }
        return this.botToken;
    }

    public String getNaverClientId() {
        if (this.naverClientId == null) {
            this.naverClientId = readToken("NaverClientId");
        }
        return this.naverClientId;
    }

    public String getNaverClientSecret() {
        if (this.naverClientSecret == null) {
            this.naverClientSecret = readToken("NaverClientSecret");
        }
        return this.naverClientSecret;
    }

    private String readToken(String path) {
        Resource resource = resourceLoader.getResource("classpath:" + path);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))){
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
