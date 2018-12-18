package com.group.artifact.vo;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenTest {
    @Test
    public void exist() throws Exception{
        ClassPathResource resource = new ClassPathResource("BotToken");
        File file = resource.getFile();
        assertThat(file.exists()).isTrue();
    }
}
