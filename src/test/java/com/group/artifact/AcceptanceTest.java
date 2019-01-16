package com.group.artifact;

import com.group.artifact.helper.JsonReader;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("DBInitializer")
public class AcceptanceTest {
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private JsonReader jsonReader;

    protected TestRestTemplate template() {
        return template;
    }

    protected ResponseEntity<String> postToThisController(String jsonFile) {
        String json = jsonReader.read(jsonFile);
        return template.postForEntity("/api/valid", makeRequest(json), String.class);
    }

    protected ResponseEntity<String> postMessage(String message) {
        java.lang.String json = jsonReader.read("format.json")
                .replace("{{message}}", message);
        return template.postForEntity("/api/valid", makeRequest(json), java.lang.String.class);
    }

    protected ResponseEntity<String> postMessage(String message, String slackId) {
        java.lang.String json = jsonReader.read("format2.json")
                .replace("{{message}}", message)
                .replace("{{user}}", slackId);
        return template.postForEntity("/api/valid", makeRequest(json), java.lang.String.class);
    }

    protected HttpEntity<String> makeRequest(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, headers);
    }

    @Test
    public void test() {
    }
}
