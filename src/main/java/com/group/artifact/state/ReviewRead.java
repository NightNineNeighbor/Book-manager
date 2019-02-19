package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.frame.NeedBookNameState;
import com.group.artifact.state.proxy.ProxyWithManyBookNames;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ReviewRead implements NeedBookNameState {
    @Override
    public String doService(SlackService service, MessageVo messageVo, StateContainer stateContainer) {
        List<Book> books = service.search(messageVo.getText());
        if (books.size() == 0) {
            service.send("해당하는 책 이름이 없습니다.", messageVo.getChannel());
            stateContainer.remove(messageVo);
            return "NO BOOK NAME";
        } else if (books.size() == 1) {
            service.readReview(books.get(0).getTitle(), messageVo.getChannel());
            stateContainer.remove(messageVo);
            return "READ REVIEW";
        } else {
            service.selectBook(messageVo.getChannel(), books);
            stateContainer.put(messageVo, new ProxyWithManyBookNames(books, this));
            return "MANY BOOK";
        }
    }

    @Override
    public String serviceWithBookName(SlackService service, MessageVo messageVo, String bookName, StateContainer stateContainer) {
        service.readReview(bookName, messageVo.getChannel());
        stateContainer.remove(messageVo);
        return "READ REVIEW";
    }
}
