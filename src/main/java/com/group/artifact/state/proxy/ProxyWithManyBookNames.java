package com.group.artifact.state.proxy;

import com.group.artifact.domain.Book;
import com.group.artifact.state.NeedBookNameState;
import com.group.artifact.state.State;
import com.group.artifact.vo.MessageVo;

import java.util.List;

public class ProxyWithManyBookNames extends State {
    private List<Book> books;
    private NeedBookNameState needBookNameState;

    public ProxyWithManyBookNames(List<Book> books, NeedBookNameState needBookNameState) {
        super(needBookNameState.getService(), needBookNameState.getStateContainer());
        this.books = books;
        this.needBookNameState = needBookNameState;
    }

    @Override
    public String doService(MessageVo messageVo) {
        String text = messageVo.getText();
        int index;
        try {
            index = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            service.send("잘못된 입력입니다.", messageVo.getChannel());
            stateContainer.remove(messageVo);
            return "WRONG INPUT";
        }

        if (index > books.size()) {
            service.send("잘못된 입력입니다", messageVo.getChannel());
            stateContainer.remove(messageVo);
            return "WRONG INPUT";
        }

        return needBookNameState.serviceWithBookName(messageVo, books.get(index-1).getTitle());
    }
}
