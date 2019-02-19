package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.state.container.StateContainer;
import com.group.artifact.state.frame.State;
import com.group.artifact.state.proxy.ProxyWithManyBookNames;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ReviewUpdate extends State {
    public ReviewUpdate(SlackService service, StateContainer stateContainer) {
        super(service, stateContainer);
    }

    @Override
    public String doService(MessageVo messageVo) {
        List<Book> books = service.search(messageVo.getText());
        if (books.size() == 0) {
            service.send("해당하는 책 이름이 없습니다.", messageVo.getChannel());
            stateContainer.remove(messageVo);
            return "NO BOOK NAME";
        } else if (books.size() == 1) {
            service.askReviewContents(messageVo.getChannel());
            stateContainer.put(messageVo, new ReviewUpdateExpectContents(service, stateContainer, books.get(0).getTitle()));
            return "ASK CONTENTS";
        } else {
            service.selectBook(messageVo.getChannel(), books);
            stateContainer.put(messageVo, new ProxyWithManyBookNames(books, new ReviewUpdateExpectContents(service, stateContainer)));
            return "MANY BOOK";
        }
    }

}
