package com.group.artifact.state;

import com.group.artifact.domain.Book;
import com.group.artifact.stateStarter.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatBotState {
    private static Map<String, State> savedState = new HashMap<>();

    public static State currentState(String slackId, Command command, List<Book> books) {
        if (books.size() == 0) {

        }
        if (books.size() == 1) {

        }
        State ret = savedState(slackId, command, books.size() == 1)
                .nextState(books.size() == 1, command);
        savedState.put(slackId, ret);
        return ret;
    }

    private static State savedState(String slackId, Command command, boolean isBookName) {
        if (savedState.containsKey(slackId)) {
            return savedState.get(slackId);
        }
        return State.of(isBookName, command);
    }
}
