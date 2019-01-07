package com.group.artifact;

import com.group.artifact.vo.NaverAcceptor;
import com.group.artifact.vo.Url;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class NaverBookApiTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(NaverBookApiTest.class);
    @Autowired
    private Url url;


    @Test
    public void naverApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "vajWHFNqfloEuhxste1B");
        headers.add("X-Naver-Client-Secret", "WW4Y6t1HSN");

        HttpEntity<Void> request = new HttpEntity<Void>(headers);

        ResponseEntity<NaverAcceptor> response = template().exchange(
                "https://openapi.naver.com/v1/search/book.json?display=1&sort=sim&query=성공하는 프로그래밍",
                HttpMethod.GET,
                request,
                NaverAcceptor.class);
        NaverAcceptor naverAcceptor = response.getBody();

        log.info("response : {}", naverAcceptor);
    }
}
