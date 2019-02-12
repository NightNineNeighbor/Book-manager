package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.SlackService;
import com.group.artifact.state_collection.ChatBotState;
import com.group.artifact.state_interface.NeedBookNameState;
import com.group.artifact.vo.MessageVo;

import java.util.List;

public class BookInfoState implements NeedBookNameState {
    @Override
    public String doService(SlackService service, MessageVo messageVo, ChatBotState chatBotState) {
        List<Book> books = service.search(messageVo.getText());
        if (books.size() == 0) {
            service.send("해당하는 책 이름이 없습니다.", messageVo.getChannel());
            chatBotState.remove(messageVo);
            return "NO BOOK NAME";
        } else if (books.size() == 1) {
            service.sendBookInfo(books.get(0).getTitle(), messageVo.getChannel());
            chatBotState.remove(messageVo);
            return "BOOK INFO";
        } else {
            service.selectBook(messageVo.getChannel(), books);
            chatBotState.put(messageVo, new ProxyWithManyBookNames(books, this));
            return "MANY BOOK";
        }
    }

    @Override
    public String serviceWithBookName(SlackService service, MessageVo messageVo, String bookName, ChatBotState chatBotState) {
        service.sendBookInfo(bookName, messageVo.getChannel());
        return "BOOK INFO";
    }
}
