package com.group.artifact.state.proxy;

import com.group.artifact.domain.Book;
import com.group.artifact.state_collection.ChatBotState;
import com.group.artifact.state.frame.NeedBookNameState;
import com.group.artifact.state.frame.State;
import com.group.artifact.vo.MessageVo;
import com.group.artifact.service.SlackService;

import java.util.List;

public class ProxyWithManyBookNames implements State {
    private List<Book> books;
    private NeedBookNameState needBookNameState;

    public ProxyWithManyBookNames(List<Book> books, NeedBookNameState needBookNameState) {
        this.books = books;
        this.needBookNameState = needBookNameState;
    }

    @Override
    public String doService(SlackService service, MessageVo messageVo, ChatBotState chatBotState) {
        String text = messageVo.getText();
        int index;
        try {
            index = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            service.send("잘못된 입력입니다.", messageVo.getChannel());
            chatBotState.remove(messageVo);
            return "WRONG INPUT";
        }

        if (index > books.size()) {
            service.send("잘못된 입력입니다", messageVo.getChannel());
            chatBotState.remove(messageVo);
            return "WRONG INPUT";
        }

        return needBookNameState.serviceWithBookName(service, messageVo, books.get(index-1).getTitle(), chatBotState);
    }
}
