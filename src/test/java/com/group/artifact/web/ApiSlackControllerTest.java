package com.group.artifact.web;

import com.group.artifact.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ApiSlackControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiSlackControllerTest.class);

    @Test
    public void echo() throws Exception {
        String json = "{\"token\":\"7zkvHfpUBwH8ku78ZmQMHRPe\",\"team_id\":\"TD80Q6XJ9\",\"api_app_id\":\"AEVEG61GA\",\"event\":{\"client_msg_id\":\"5fbfd775-b066-4def-b338-855ae14907c7\",\"type\":\"message\",\"text\":\"야호~!\",\"user\":\"UD7QN97QS\",\"ts\":\"1545138254.002500\",\"channel\":\"CD7KGTJ3E\",\"event_ts\":\"1545138254.002500\",\"channel_type\":\"channel\"},\"type\":\"event_callback\",\"event_id\":\"EvEWK8HPV1\",\"event_time\":1545138254,\"authed_users\":[\"UEVBEU0AG\"]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = template().postForEntity("/api/valid", request, String.class);
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).isEqualTo("OK");
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