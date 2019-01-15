package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ReviewCreate implements State {
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        List<Book> books = service.search(serviceResolver.getText());
        if (books.size() == 1) {
            service.askReviewContents(serviceResolver.getChannel());
            ChatBotState.put(serviceResolver.getSlackId(), new ReviewCreateExpectContents(books.get(0).getTitle()));
            return "ASK CONTENTS";
        } else if (books.size() == 0) {
            service.askBookName(serviceResolver.getChannel());
            ChatBotState.put(serviceResolver.getSlackId(), new ReviewCreateExpectBook());
            return "ASK BOOK NAME";
        } else {
            service.selectBook(serviceResolver.getChannel(), books);
            ChatBotState.put(serviceResolver.getSlackId(),new ManyBooks(books, new ReviewCreateExpectContents()));
            return "MANY BOOK";
        }
    }

}
