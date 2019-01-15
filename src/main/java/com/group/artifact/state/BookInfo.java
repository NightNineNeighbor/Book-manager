package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

import java.util.List;

public class BookInfo implements State{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        List<Book> books = service.search(serviceResolver.getText());
        if (books.size() == 1) {
            service.sendBookInfo(books.get(0).getTitle(), serviceResolver.getChannel());
            ChatBotState.put(serviceResolver.getSlackId(), StateZero.me);
            return "BOOK INFO";
        }

        return null;
    }
}
