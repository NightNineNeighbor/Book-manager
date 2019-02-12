package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.SlackService;
import com.group.artifact.stateStarter.Command;
import com.group.artifact.state_collection.ChatBotState;
import com.group.artifact.state_interface.State;
import com.group.artifact.vo.MessageVo;

import java.util.List;

public class ReviewUpdateExpectBook implements State {

    @Override
    public String doService(SlackService service, MessageVo messageVo, ChatBotState chatBotState) {
        Command command = messageVo.getCommand();
        if (command == Command.NO_COMMAND) {
            List<Book> books = service.search(messageVo.getText());
            if (books.size() == 0) {
                service.send("해당하는 책 이름이 없습니다.", messageVo.getChannel());
                chatBotState.remove(messageVo);
                return "NO BOOK NAME";
            } else if (books.size() == 1) {
                service.askReviewContents(messageVo.getChannel());
                chatBotState.put(messageVo, new ReviewUpdateExpectContents(books.get(0).getTitle()));
                return "ASK REVIEW CONTENTS";
            } else {
                service.selectBook(messageVo.getChannel(), books);
                chatBotState.put(messageVo, new ProxyWithManyBookNames(books, new ReviewUpdateExpectContents()));
                return "MANY BOOK";
            }
        }
        return command.initState().doService(service, messageVo, chatBotState);
    }


}
