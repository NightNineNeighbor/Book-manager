package com.group.artifact.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.group.artifact.helper.HtmlFormDataBuilder;
import com.group.artifact.vo.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service("slackService")
public class SlackService {
    @Autowired
    Token token;

    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> echo(JsonNode jsonNode) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("token", token.getBotToken())
                .addParameter("text", "<@" + jsonNode.get("user").asText() + ">'s Echo~~~ " + jsonNode.get("text").asText())
                .addParameter("channel", jsonNode.get("channel").asText())
                .build();

        ResponseEntity<String> response = restTemplate.postForEntity("https://slack.com/api/chat.postMessage", request, String.class);
        return response;

    }
}
