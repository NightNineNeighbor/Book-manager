package com.group.artifact.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group.artifact.AcceptanceTest;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ApiSlackControllerTest extends AcceptanceTest {

    @Test
    public void valid() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String request = "{\"token\":\"7zkvHfpUBwH8ku78ZmQMHRPe\",\"team_id\":\"TD80Q6XJ9\",\"api_app_id\":\"AEVEG61GA\",\"event\":{\"client_msg_id\":\"5fbfd775-b066-4def-b338-855ae14907c7\",\"type\":\"message\",\"text\":\"야호~!\",\"user\":\"UD7QN97QS\",\"ts\":\"1545138254.002500\",\"channel\":\"CD7KGTJ3E\",\"event_ts\":\"1545138254.002500\",\"channel_type\":\"channel\"},\"type\":\"event_callback\",\"event_id\":\"EvEWK8HPV1\",\"event_time\":1545138254,\"authed_users\":[\"UEVBEU0AG\"]}";
        Map<String, Object> map = new HashMap<String, Object>();

        map = mapper.readValue(request, new TypeReference<Map<String, Object>>() {
        });

        ResponseEntity<String> response = template().postForEntity("/api/valid", map, String.class);
        System.out.println(response.toString());
    }
}