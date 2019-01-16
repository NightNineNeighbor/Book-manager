package com.group.artifact.vo;

import com.group.artifact.AcceptanceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(TokenTest.class);

    @Autowired
    private Token token;

    @Test
    public void botToken() throws Exception {
        String botToken = token.getBotToken();
        log.info("botToken {}", botToken);
        softly.assertThat(botToken).isNotNull();
    }

    @Test
    public void naverClientId() throws Exception {
        String naverClientId = token.getNaverClientId();
        log.info("naverClientId {}", naverClientId);
        softly.assertThat(naverClientId).isNotNull();
    }

    @Test
    public void NaverClientSecret() throws Exception {
        String naverClientSecret = token.getNaverClientSecret();
        log.info("naverClientSecret {}", naverClientSecret);
        softly.assertThat(naverClientSecret).isNotNull();
    }
}
