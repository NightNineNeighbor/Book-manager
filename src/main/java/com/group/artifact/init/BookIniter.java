package com.group.artifact.init;

import com.group.artifact.Exception.StopCrawlException;
import com.group.artifact.domain.Book;
import com.group.artifact.domain.BookRepository;
import com.group.artifact.domain.Review;
import com.group.artifact.domain.ReviewRepository;
import com.group.artifact.sender.MessageSender;
import com.group.artifact.vo.CrawledBook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class BookIniter {
    private static final Logger log = LoggerFactory.getLogger(BookIniter.class);

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ResourceLoader resourceLoader;


    @EventListener(ApplicationReadyEvent.class)
    public void initBook() {
        Resource resource = resourceLoader.getResource("classpath:BookNames");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String bookName;
            while ((bookName = reader.readLine()) != null) {
                Book book = crawlAndSaveBook(bookName);
                crawlAndSaveReview(bookName, book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (StopCrawlException e) {
            log.error(e.getMessage());
        }
    }

    private void crawlAndSaveReview(String bookName, Book book) {
        List<String> reviews = messageSender.crawlReviews(bookName);

        for (String review : reviews) {
            reviewRepository.save(Review.of(review, book));
        }
    }

    private Book crawlAndSaveBook(String bookName) throws IOException {
        CrawledBook crawledBook = messageSender.crawlBook(bookName);
        String contents = messageSender.crawlBookContents(crawledBook.getLink());

        return bookRepository.save(Book.of(crawledBook, contents));
    }
}
