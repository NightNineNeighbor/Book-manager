package com.group.artifact.web;

import com.group.artifact.AcceptanceTest;
import com.group.artifact.helper.JsonReader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ApiSlackControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiSlackControllerTest.class);
    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void matchBookName(){
        Resource resource = resourceLoader.getResource("classpath:requestBookInfo.json");
        String json = JsonReader.read(resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = template().postForEntity("/api/valid", request, String.class);
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("SEND BOOK INFO");
    }

    @Test
    public void echo() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:messageEvent.json");
        String json = JsonReader.read(resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = template().postForEntity("/api/valid", request, String.class);
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).isEqualTo("ECHO");
    }

    @Test
    public void eventSubscription() {
        String challengeToken = "3eZbrw1aBm2rZgRNFdxV2595E9CY3gmdALWMmHkvFXO7tYXAYM8P";
        String json = "{\"token\": \"Jhj5dZrVaK7ZwHHjRyZWjbDl\",\"challenge\": \"" + challengeToken + "\",\"type\": \"url_verification\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = template().postForEntity("/api/valid", request, String.class);
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).isEqualTo(challengeToken);
    }
}