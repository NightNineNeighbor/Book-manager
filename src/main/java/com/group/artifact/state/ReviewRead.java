package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ReviewRead implements State, NeedBookName{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        List<Book> books = service.search(serviceResolver.getText());
        if (books.size() == 0) {
            service.askBookName(serviceResolver.getChannel());
            ChatBotState.put(serviceResolver.getSlackId(), this);
            return "ASK BOOK NAME";
        } else if (books.size() == 1) {
            service.readReview(books.get(0).getTitle(), serviceResolver.getChannel());
            ChatBotState.put(serviceResolver.getSlackId(), StateZero.me);
            return "READ REVIEW";
        } else {
            service.selectBook(serviceResolver.getChannel(), books);
            ChatBotState.put(serviceResolver.getSlackId(), new ProxyWithManyBookNames(books, this));
            return "MANY BOOK";
        }
    }

    @Override
    public String serviceWithBookName(SlackService service, ServiceResolver serviceResolver, String bookName) {
        service.readReview(bookName, serviceResolver.getChannel());
        return "READ REVIEW";
    }
}
