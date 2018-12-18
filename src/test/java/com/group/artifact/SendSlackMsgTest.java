package com.group.artifact;

import com.group.artifact.helper.HtmlFormDataBuilder;
import com.group.artifact.vo.Token;
import com.group.artifact.vo.Url;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

public class SendSlackMsgTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(SendSlackMsgTest.class);

    @Autowired
    private Token token;

    @Test
    public void webhookTest() {
        Map<String, String> request = new HashMap<>();
        request.put("channel", "#general");
        request.put("username", "NNNbot");
        request.put("text", "NNN is testing a bot.");
        request.put("icon_emoji", ":smirk_cat:");

        ResponseEntity<String> response = template().postForEntity(Url.WEBHOOK, request, String.class);
        log.info("response : {}", response.toString());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void postMessageTest() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("token", token.getBotToken())
                .addParameter("channel", "CD7KGTJ3E")
                .addParameter("text", "test message to <@UD7QN97QS>")
                .build();

        ResponseEntity<String> response = template().postForEntity(Url.POST_MESSAGE, request, String.class);

        log.info("request : {}", request.toString());
        log.info("response : {}", response.toString());
        softly.assertThat(response.getBody()).contains("\"ok\":true");
    }
}
