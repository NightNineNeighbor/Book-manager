package com.group.artifact;

import com.group.artifact.vo.Url;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AladinCrawlTest extends AcceptanceTest {

    @Autowired
    private Url url;

    @Test
    public void crawlAladin() throws Exception {
        String bookName = "어린 왕자";
        String searchLink = url.getAladinSearch(bookName);
        Document doc = Jsoup.connect(searchLink).get();
        Element contents = doc.select(".ss_book_box").first();
        String itemId = contents.attr("itemid");

        String ajaxLink = url.getAladinAjax(itemId);
        Document doc1 = Jsoup.connect(ajaxLink).get();
        Elements contents1 = doc1.select(".re_text span");

        List<String> reviews = contents1.eachText();

        for (int i = reviews.size() - 2; i >= 0; i = i - 2) {
            reviews.remove(i);
        }

        for (String review : reviews) {
            System.out.println(review);
        }
    }
}
