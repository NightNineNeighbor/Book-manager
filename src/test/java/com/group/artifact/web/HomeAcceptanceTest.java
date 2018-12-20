package com.group.artifact.web;

import com.group.artifact.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HomeAcceptanceTest extends AcceptanceTest {
    Logger log = LoggerFactory.getLogger(AcceptanceTest.class);

    @Test
    public void homeTest() {
        ResponseEntity<String> response = template().getForEntity("/", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }




}

