package com.group.artifact.web;

import com.group.artifact.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public class ApiSlackControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiSlackControllerTest.class);

    @Test
    public void matchBookName() {
        ResponseEntity<String> response = postToThisController("requestBookInfo.json");
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("SEND BOOK INFO");
    }

    @Test
    public void echo() throws Exception {
        ResponseEntity<String> response = postToThisController("messageEvent.json");
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).isEqualTo("ECHO");
    }

    @Test
    public void eventSubscription() {
        String challengeToken = "3eZbrw1aBm2rZgRNFdxV2595E9CY3gmdALWMmHkvFXO7tYXAYM8P";
        String json = "{\"token\": \"Jhj5dZrVaK7ZwHHjRyZWjbDl\",\"challenge\": \"" + challengeToken + "\",\"type\": \"url_verification\"}";

        HttpEntity<String> request = makeRequest(json);

        ResponseEntity<String> response = template().postForEntity("/api/valid", request, String.class);
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).isEqualTo(challengeToken);
    }
}