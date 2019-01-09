package com.group.artifact.Exception;

public class StopCrawlException extends RuntimeException {
    public static final String JSOUP_CONNECT_IOEXCEPTION = "Jsoup.connect.get() IOException";

    public StopCrawlException(String message) {
        super(message);
    }
}
