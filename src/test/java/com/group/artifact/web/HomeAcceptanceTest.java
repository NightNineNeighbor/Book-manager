package com.group.artifact.web;

import com.group.artifact.AcceptanceTest;
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

public class HomeAcceptanceTest extends AcceptanceTest {
    Logger log = LoggerFactory.getLogger(AcceptanceTest.class);

    @Autowired
    Token token;

    @Test
    public void homeTest() {
        ResponseEntity<String> response = template().getForEntity("/", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }




}

