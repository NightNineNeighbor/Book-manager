package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ManyBooks implements State {
    private List<Book> books;
    private NeedBook needBook;

    public ManyBooks(List<Book> books) {
        this.books = books;
    }

    public ManyBooks(List<Book> books, NeedBook needBook) {
        this.books = books;
        this.needBook = needBook;
    }

    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        String text = serviceResolver.getText();
        int index;
        try {
            index = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            service.error("잘못된 입력입니다.");
            return "WRONG INPUT";
        }

        if (index > books.size()) {
            service.error("잘못된 입력입니다");
            return "WRONG INPUT";
        }

        needBook.beforeService(service, serviceResolver);
        ChatBotState.put(serviceResolver.getSlackId(), needBook.setBookName(books.get(0).getTitle()));
        return "BOOK SAVED";
    }
}
