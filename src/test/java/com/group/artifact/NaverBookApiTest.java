package com.group.artifact;

import com.group.artifact.vo.NaverAcceptor;
import com.group.artifact.vo.Token;
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

    @Autowired
    private Token token;

    @Test
    public void naverApi() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", token.getNaverClientId());
        headers.add("X-Naver-Client-Secret", token.getNaverClientSecret());

        HttpEntity<Void> request = new HttpEntity<Void>(headers);

        ResponseEntity<NaverAcceptor> response = template().exchange(
                url.getNaverApi("성공하는 프로그래밍 공부법"),
                HttpMethod.GET,
                request,
                NaverAcceptor.class);
        NaverAcceptor naverAcceptor = response.getBody();

        log.info("response : {}", naverAcceptor);
    }
}
