package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ProxyWithManyBookNames implements State {
    private List<Book> books;
    private NeedBookName needBookName;

    public ProxyWithManyBookNames(List<Book> books, NeedBookName needBookName) {
        this.books = books;
        this.needBookName = needBookName;
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

        return needBookName.serviceWithBookName(service, serviceResolver, books.get(index-1).getTitle());
    }
}
