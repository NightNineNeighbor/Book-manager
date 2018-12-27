package com.group.artifact;

import com.group.artifact.helper.JsonReader;
import com.group.artifact.vo.Token;
import com.group.artifact.vo.Url;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

public class SendSlackMsgTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(SendSlackMsgTest.class);
    @Autowired
    private Token token;

    @Autowired
    private Url url;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void webhookTest() {
        Map<String, String> request = new HashMap<>();
        request.put("channel", "#general");
        request.put("username", "NNNbot");
        request.put("text", "NNN is testing a bot.");
        request.put("icon_emoji", ":smirk_cat:");

        ResponseEntity<String> response = template().postForEntity(url.getWebhook(), request, String.class);
        log.info("response : {}", response.toString());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postMessageTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token.getBotToken());

        Map<String, String> map = new HashMap<>();
        map.put("channel", "CD7KGTJ3E");
        map.put("text", "test message to <@UD7QN97QS>");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = template().postForEntity(url.getPostMessage(), request, String.class);

        log.info("request : {}", request.toString());
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }

    @Test
    public void bookInfo(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token.getBotToken());

        Resource resource = resourceLoader.getResource("classpath:responseBookInfo.json");
        String json = JsonReader.read(resource);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = template().postForEntity(url.getPostMessage(), request, String.class);

        log.info("request : {}", request.toString());
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }
}
