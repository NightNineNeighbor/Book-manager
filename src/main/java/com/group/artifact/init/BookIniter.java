package com.group.artifact.init;

import com.group.artifact.domain.Book;
import com.group.artifact.domain.BookRepository;
import com.group.artifact.sender.MessageSender;
import com.group.artifact.vo.CrawledBook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class BookIniter {
    @Autowired
    private MessageSender messageSender;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @EventListener(ApplicationReadyEvent.class)
    public void initBook() {
        Resource resource = resourceLoader.getResource("classpath:BookNames");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String bookName = reader.readLine();
            while (bookName != null) {
                crawlAndSave(bookName);
                bookName = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crawlAndSave(String bookName) throws IOException {
        CrawledBook crawledBook = messageSender.crawlBook(bookName);
        String link = crawledBook.getLink();

        String contents = crawlContents(link);

        bookRepository.save(Book.of(crawledBook, contents));
    }

    private String crawlContents(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements contents = doc.select("#tableOfContentsContent p");
        return contents.html().replace("<br>", "\\n");
    }
}
