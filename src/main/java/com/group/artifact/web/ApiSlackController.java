package com.group.artifact.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.group.artifact.service.SlackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiSlackController {
    private static final Logger logger = LoggerFactory.getLogger(ApiSlackController.class);

    @Autowired
    SlackService slackService;

    //todo : change mapping url
    @PostMapping("/valid")
    public String valid(@RequestBody JsonNode jsonNode) throws Exception {
        logger.info("validation msg : {}", jsonNode.toString());

        if ("message".equals(jsonNode.path("event").path("type").asText())) {
            slackService.echo(jsonNode);
            return "OK";
        }
        return jsonNode.get("challenge").asText();
    }
}
