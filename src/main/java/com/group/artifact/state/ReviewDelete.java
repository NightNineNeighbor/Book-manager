package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.service.SlackService;
import com.group.artifact.state_collection.ChatBotState;
import com.group.artifact.state.frame.NeedBookNameState;
import com.group.artifact.state.proxy.ProxyWithManyBookNames;
import com.group.artifact.vo.MessageVo;

import java.util.List;

public class ReviewDelete implements NeedBookNameState {
    @Override
    public String doService(SlackService service, MessageVo messageVo, ChatBotState chatBotState) {
        List<Book> books = service.search(messageVo.getText());
        if (books.size() == 0) {
            service.send("해당하는 책 이름이 없습니다.", messageVo.getChannel());
            chatBotState.remove(messageVo);
            return "NO BOOK NAME";
        } else if (books.size() == 1) {
            service.deleteReview(books.get(0).getTitle(), messageVo.getSlackId(), messageVo.getChannel());
            chatBotState.remove(messageVo);
            return "DELETE REVIEW";
        } else {
            service.selectBook(messageVo.getChannel(), books);
            chatBotState.put(messageVo, new ProxyWithManyBookNames(books, this));
            return "MANY BOOK";
        }
    }

    @Override
    public String serviceWithBookName(SlackService service, MessageVo messageVo, String bookName, ChatBotState chatBotState) {
        service.deleteReview(bookName, messageVo.getSlackId(), messageVo.getChannel());
        chatBotState.remove(messageVo);
        return "DELETE REVIEW";
    }

}
