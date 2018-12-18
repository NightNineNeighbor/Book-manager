package com.group.artifact.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.artifact.AcceptanceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class SlackServiceTest extends AcceptanceTest {

    @Autowired
    SlackService slackService;

    @Test
    public void echo() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put("user", "UD7QN97QS");
        map.put("text", "test text");
        map.put("channel","CD7KGTJ3E");
        JsonNode jsonNode = mapper.valueToTree(map);

        ResponseEntity<String> response = slackService.echo(jsonNode);
        System.out.println(response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }
}