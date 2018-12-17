package com.group.artifact.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ApiSlackController {
    private static final Logger logger = LoggerFactory.getLogger(ApiSlackController.class);

    @PostMapping("/valid")
    public String valid(@RequestBody Map<String,Object> map) throws Exception{
        logger.info("validation msg : {}", map.toString());
        if (map.containsKey("challenge")) {
            return (String) map.get("challenge");
        }
        echo(map);
        return "OK";
    }

    private String echo(Map<String, Object> map) throws Exception {
        URL url = new URL("https://slack.com/api/chat.postMessage");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        Map<String, Object> event = (Map<String, Object>) map.get("event");
        byte[] text = ("text=<@" + event.get("user") + ">" + event.get("text")+"&" +
                "token=xoxb-450024235621-505388952356-JojzHEUnPQ5VUZ2zY8XhqXKy&" +
                "channel=" + event.get("channel")).getBytes(StandardCharsets.UTF_8);
        con.getOutputStream().write(text);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

        String inputLine;
        while ((inputLine = in.readLine()) != null) { // response 출력
            System.out.println(inputLine);
        }

        in.close();
        con.disconnect();
        return null;
    }
}
