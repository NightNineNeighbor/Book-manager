package com.group.artifact.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.group.artifact.helper.HtmlFormDataBuilder;
import com.group.artifact.service.SlackService;
import com.group.artifact.vo.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ApiSlackController {
    private static final Logger logger = LoggerFactory.getLogger(ApiSlackController.class);

    @Autowired
    SlackService slackService;

    @Autowired
    Token token;

    @PostMapping("/valid")
    public String valid(@RequestBody JsonNode jsonNode) throws Exception{
        logger.info("validation msg : {}", jsonNode.toString());

        if (jsonNode.get("challenge").isMissingNode() && jsonNode.has("user")) {
            slackService.echo(jsonNode);
            return "OK";
        }
        return jsonNode.get("challenge").asText();
    }

}
