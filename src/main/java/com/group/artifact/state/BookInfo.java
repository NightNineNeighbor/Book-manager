package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

import java.util.List;

public class BookInfo implements State, NeedBookName{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        List<Book> books = service.search(serviceResolver.getText());
        if (books.size() == 0) {
            service.send("해당하는 책 이름이 없습니다.", serviceResolver.getChannel());
            ChatBotState.remove(serviceResolver.getSlackId());
            return "NO BOOK NAME";
        } else if (books.size() == 1) {
            service.sendBookInfo(books.get(0).getTitle(), serviceResolver.getChannel());
            ChatBotState.remove(serviceResolver.getSlackId());
            return "BOOK INFO";
        } else {
            ChatBotState.put(serviceResolver.getSlackId(), new ProxyWithManyBookNames(books, this));
            return "MANY BOOK";
        }
    }

    @Override
    public String serviceWithBookName(SlackService service, ServiceResolver serviceResolver, String bookName) {
        service.sendBookInfo(bookName, serviceResolver.getChannel());
        return "BOOK INFO";
    }
}
