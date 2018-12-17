package com.group.artifact.web;

import com.group.artifact.vo.ValidationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ApiSlackController {
    private static final Logger logger = LoggerFactory.getLogger(ApiSlackController.class);

    @PostMapping("/valid")
    public String valid(@RequestBody Map<String,Object> map) {
        logger.info("validation msg : {}", map.toString());
        if (map.containsKey("challenge")) {
            return (String) map.get("challenge");
        }
        return doSomething(map);
    }

    private String doSomething(Map<String, Object> map) {
        return null;
    }
}
