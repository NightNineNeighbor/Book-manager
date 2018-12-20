package com.group.artifact.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class Token {
    private String botToken;

    @Autowired
    private ResourceLoader resourceLoader;

    public String getBotToken() {
        if (botToken == null) {
            readToken();
        }
        return botToken;
    }

    private void readToken() {
        Resource resource = resourceLoader.getResource("classpath:BotToken");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))){
            botToken = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
