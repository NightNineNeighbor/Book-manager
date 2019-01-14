package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.ServiceResolver;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;

import java.util.List;

public class ReviewCreateExpectBook implements State {

    @Override
    public String doService(SlackService service, ServiceResolver serviceResolver) {
        Command command = serviceResolver.getCommand();
        if (command == Command.NO_COMMAND) {
            List<Book> books = service.search(serviceResolver.getText());
            if (books.size() == 0) {
                service.askBookName(serviceResolver.getChannel());
                return "ASK BOOK NAME";
            } else if (books.size() == 1) {
                service.createReview(books.get(0).getTitle(), serviceResolver.getText(), serviceResolver.getSlackId());
                ChatBotState.put(serviceResolver.getSlackId(), StateZero.me);
                return "CREATE REVIEW";
            } else {
                service.selectBook(serviceResolver.getChannel(), books);
                ChatBotState.put(serviceResolver.getSlackId(), new ManyBooks(books, new ReviewCreateExpectContents()));
                return "MANY BOOK";
            }
        }
        return command.initState().doService(service, serviceResolver);
    }


}
