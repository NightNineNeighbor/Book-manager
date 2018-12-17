package com.group.artifact.web;

import com.group.artifact.vo.ValidationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiSlackController {
    Logger logger = LoggerFactory.getLogger(ApiSlackController.class);

    @PostMapping("/valid")
    public String valid(@RequestBody ValidationVO vo) {
        logger.info("validation msg : {}", vo.toString());
        return vo.getChallenge();
    }
}
