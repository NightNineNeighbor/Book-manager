package com.group.artifact.sender;

import com.group.artifact.Exception.StopCrawlException;
import com.group.artifact.domain.Book;
import com.group.artifact.domain.Review;
import com.group.artifact.helper.RequestCreator;
import com.group.artifact.vo.CrawledBook;
import com.group.artifact.vo.NaverAcceptor;
import com.group.artifact.vo.SlackAcceptor;
import com.group.artifact.vo.Url;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Component
public class MessageSender {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Url url;

    @Autowired
    private RequestCreator requestCreator;

    public List<String> crawlReviews(String bookName)  {
        List<String> reviews;
        try {
            String searchLink = url.getAladinSearch(bookName);
            String itemId = Jsoup.connect(searchLink).get()
                    .select(".ss_book_box").first()
                    .attr("itemid");

            String ajaxLink = url.getAladinAjax(itemId);
            reviews = Jsoup.connect(ajaxLink).get()
                    .select(".re_text span")
                    .eachText();
        } catch (IOException e) {
            throw new StopCrawlException(StopCrawlException.JSOUP_CONNECT_IOEXCEPTION);
        }

        for (int i = reviews.size()-2; i >=0 ; i = i-2) {
            reviews.remove(i);
        }

        return reviews;
    }

    public String crawlBookContents(String link) {
        try {
            Document doc = Jsoup.connect(link).get();
            Elements contents = doc.select("#tableOfContentsContent p");
            return contents.html().replace("<br>", "\\n");
        } catch (IOException e) {
            throw new StopCrawlException(StopCrawlException.JSOUP_CONNECT_IOEXCEPTION);
        }
    }

    public CrawledBook crawlBook(String bookName) {
        return restTemplate.exchange(
                url.getNaverApi(bookName),
                HttpMethod.GET,
                requestCreator.crawlingHeader(),
                NaverAcceptor.class).getBody()
                .getFirstItem();
    }

    public ResponseEntity<String> echo(SlackAcceptor acceptor) {
        return restTemplate.postForEntity(url.getPostMessage(),
                requestCreator.echo(acceptor),
                String.class);
    }

    public ResponseEntity<String> bookInfo(Book book, String channel) {
        return restTemplate.postForEntity(url.getPostMessage(),
                requestCreator.bookInfo(book, channel),
                String.class);
    }

    public ResponseEntity<String> review(List<Review> reviews, String channel) {
        return restTemplate.postForEntity(url.getPostMessage(),
                requestCreator.review(reviews, channel),
                String.class);
    }


    public ResponseEntity<String> send(String text, String channel) {
        return restTemplate.postForEntity(url.getPostMessage(),
                requestCreator.simpleText(text, channel),
                String.class);
    }

}
