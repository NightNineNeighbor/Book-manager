package com.group.artifact;

import com.group.artifact.helper.JsonReader;
import com.group.artifact.vo.Token;
import com.group.artifact.vo.Url;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

public class NaverBookApiTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(NaverBookApiTest.class);
    @Autowired
    private Url url;


    @Test
    public void webhookTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "vajWHFNqfloEuhxste1B");
        headers.add("X-Naver-Client-Secret", "WW4Y6t1HSN");

        HttpEntity<Void> request = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> response = template().exchange(
                "https://openapi.naver.com/v1/search/book.json?display=1&sort=sim&query=성공하는 프로그래밍",
                HttpMethod.GET,
                request,
                String.class);

        log.info("response : {}", response.toString());
    }
}
