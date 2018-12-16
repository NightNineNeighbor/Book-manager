package com.group.artifact;

import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {
    @Autowired
    private TestRestTemplate template;

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();

    TestRestTemplate template(){
        return template;
    }

}
