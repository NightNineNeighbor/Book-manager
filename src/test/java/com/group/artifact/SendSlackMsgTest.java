package com.group.artifact;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SendSlackMsgTest {
    private static final String URL = "https://hooks.slack.com/services/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";
    private static final String DATA = "{\"channel\": \"#general\",\"username\": \"NNNbot\",\"text\": \"NNN is testing bot.\",\"icon_emoji\": \":smirk_cat:\"}";

    @Test
    public void sendMsg() throws Exception {
        URL url = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setDoOutput(true);

        byte[] out = DATA.getBytes(StandardCharsets.UTF_8);
        con.getOutputStream().write(out);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

        String inputLine;
        while ((inputLine = in.readLine()) != null) { // response 출력
            System.out.println(inputLine);
        }

        in.close();
        con.disconnect();
    }

}
