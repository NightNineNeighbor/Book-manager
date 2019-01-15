package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ReviewDelete implements State, NeedBook {
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        List<Book> books = service.search(serviceResolver.getText());
        if (books.size() == 0) {
            service.askBookName(serviceResolver.getChannel());
            ChatBotState.put(serviceResolver.getSlackId(), this);
            return "ASK BOOK NAME";
        } else if (books.size() == 1) {
            service.deleteReview(books.get(0).getTitle(), serviceResolver.getSlackId(), serviceResolver.getChannel());
            ChatBotState.put(serviceResolver.getSlackId(), StateZero.me);
            return "DELETE REVIEW";
        } else {
            service.selectBook(serviceResolver.getChannel(), books);
            ChatBotState.put(serviceResolver.getSlackId(), new ManyBooks(books, this));
            return "MANY BOOK";
        }
    }

    @Override
    public String serviceWithBookName(SlackService service, ServiceResolver serviceResolver, String bookName) {
        service.deleteReview(bookName, serviceResolver.getSlackId(), serviceResolver.getChannel());
        ChatBotState.put(serviceResolver.getSlackId(), StateZero.me);
        return "DELETE REVIEW";
    }

}
