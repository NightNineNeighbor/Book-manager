package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ReviewRead implements State{
    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        List<Book> books = service.search(serviceResolver.getText());
        if (books.size() == 1) {
            service.readReview(books.get(0).getTitle(), serviceResolver.getChannel());
            return "READ REVIEW";
        }
        return null;
    }
}
