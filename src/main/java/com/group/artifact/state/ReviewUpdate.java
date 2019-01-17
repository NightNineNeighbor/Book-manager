package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ReviewUpdate implements State {
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        List<Book> books = service.search(serviceResolver.getText());
        if (books.size() == 0) {
            service.send("해당하는 책 이름이 없습니다.", serviceResolver.getChannel());
            ChatBotState.remove(serviceResolver.getSlackId());
            return "NO BOOK NAME";
        } else if (books.size() == 1) {
            service.askReviewContents(serviceResolver.getChannel());
            ChatBotState.put(serviceResolver.getSlackId(), new ReviewUpdateExpectContents(books.get(0).getTitle()));
            return "ASK CONTENTS";
        } else {
            service.selectBook(serviceResolver.getChannel(), books);
            ChatBotState.put(serviceResolver.getSlackId(), new ProxyWithManyBookNames(books, new ReviewUpdateExpectContents()));
            return "MANY BOOK";
        }
    }

}
