package com.group.artifact;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

public class CrawlingTest {
    @Test
    public void crawl() throws Exception {
        Document doc = Jsoup.connect("http://book.naver.com/bookdb/book_detail.php?bid=13993648").get();
        Elements contents = doc.select("#tableOfContentsContent p");
        String html = contents.html();
        System.out.println(html.replace("<br>", System.lineSeparator()));
    }
}
