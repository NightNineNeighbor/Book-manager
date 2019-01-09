package com.group.artifact.vo;

import com.group.artifact.Exception.StopCrawlException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Component
public class Url {
    @Value("${webhook}")
    private String webhook;
    @Value("${postMessage}")
    private String postMessage;
    @Value("${naverApi}")
    private String naverApi;

    @Value("${aladinSearch}")
    private String aladinSearch;

    @Value("${aladinAjax}")
    private String aladinAjax;
    public Url() {
    }

    public String getWebhook() {
        return webhook;
    }


    public String getPostMessage() {
        return postMessage;
    }

    public String getNaverApi() {
        return naverApi;
    }

    public String getNaverApi(String bookName) {
        return naverApi + "?display=1&sort=sim&query=" + bookName;
    }

    public String getAladinSearch() {
        return aladinSearch;
    }

    public String getAladinSearch(String bookName) {
        try {
            return aladinSearch + "SearchWord=" + URLEncoder.encode(bookName, "EUC-KR");
        } catch (UnsupportedEncodingException e) {
            throw new StopCrawlException("책이름 인코딩 예외");
        }
    }

    public String getAladinAjax() {
        return aladinAjax;
    }

    public String getAladinAjax(String itemId) {
        return aladinAjax + "ProductItemId=" + itemId + "&itemId=" + itemId;
    }
}
